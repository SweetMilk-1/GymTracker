package ru.sweetmilk.gymtracker.fragments.trainings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.Training
import ru.sweetmilk.gymtracker.data.repositories.TrainingRepo
import ru.sweetmilk.gymtracker.trainingManager.TrainingManager
import java.util.UUID
import javax.inject.Inject

typealias OpenExercisesParameters = Pair<UUID, String>

class TrainingsViewModel @Inject constructor(
    private val trainingRepo: TrainingRepo,
    private val trainingManager: TrainingManager
) : ViewModel() {
    //LiveData
    val items: LiveData<Result<List<Training>>> = trainingRepo.getAllTrainingsObservable()

    val isEmpty: LiveData<Boolean> = items.map {
        when (it) {
            is Result.Success -> it.data.isEmpty()
            else -> false
        }
    }

    fun startTraining() {
        viewModelScope.launch {
            trainingManager.startTraining()
        }
    }
}