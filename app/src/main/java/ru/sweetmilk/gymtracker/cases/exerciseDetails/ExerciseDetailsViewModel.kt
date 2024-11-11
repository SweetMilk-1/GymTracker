package ru.sweetmilk.gymtracker.cases.exerciseDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import java.util.UUID
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
) : ViewModel() {

    //Live Data
    private var exerciseId = MutableLiveData<UUID>()

    private val exercise = exerciseId.switchMap {
        exerciseRepo.getExerciseByIdObservable(it).map {
            when (it) {
                is Result.Success -> it.data
                else -> {
                    showSnackBarMessageEvent.value = R.string.could_not_load_data
                    null
                }
            }
        }
    }

    val name: LiveData<String?> = exercise.map {
        it?.name
    }

    val description: LiveData<String?> = exercise.map {
        it?.description
    }

    val hasDuration: LiveData<Boolean?> = exercise.map {
        it?.hasDuration
    }

    val isEmpty: LiveData<Boolean> = exercise.map {
        it == null
    }

    //Events
    val showSnackBarMessageEvent = SingleLiveEvent<Int>()
    val updateExerciseEvent = SingleLiveEvent<UUID>()
    val deleteExerciseCompletedEvent = SingleLiveEvent<Unit>()

    private var isInitialized: Boolean = false

    fun init(id: UUID?) {
        if (isInitialized)
            return

        if (id == null) {
            showSnackBarMessageEvent.value = R.string.could_not_load_data
            return
        }

        exerciseId.value = id!!

        isInitialized = true
    }

    fun updateExercise() {
        if (exerciseId.value == null)
            return
        updateExerciseEvent.value = exerciseId.value
    }

    fun deleteExercise() {
        viewModelScope.launch {
            val exercise = exercise.value
            if (exercise != null) {
                exerciseRepo.deleteExercise(exercise)
                deleteExerciseCompletedEvent.value = Unit
            }
        }
    }
}