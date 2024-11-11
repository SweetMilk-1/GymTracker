package ru.sweetmilk.gymtracker.cases.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import java.util.UUID
import javax.inject.Inject

typealias OpenExercisesParameters = Pair<UUID, String>

class ExercisesViewModel @Inject constructor(
    exerciseRepo: ExerciseRepo
) : ViewModel() {
    //LiveData
    val items: LiveData<Result<List<Exercise>>> = exerciseRepo.getAllExercisesObservable()

    val isEmpty: LiveData<Boolean> = items.map {
        when (it) {
            is Result.Success -> it.data.isEmpty()
            else -> false
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    //Events
    val createNewExerciseEvent = SingleLiveEvent<Unit>()
    val openExerciseEvent = SingleLiveEvent<OpenExercisesParameters>()


    fun createNewExercise() {
        createNewExerciseEvent.value = Unit
    }

    fun openExercise(id: UUID, title: String) {
        openExerciseEvent.value = OpenExercisesParameters(id, title)
    }
}