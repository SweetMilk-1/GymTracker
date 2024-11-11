package ru.sweetmilk.gymtracker.cases.addEditExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import ru.sweetmilk.gymtracker.data.Result
import java.util.UUID
import javax.inject.Inject

class AddEditExerciseViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
) : ViewModel() {
    //Live Data
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String?>()
    val hasDuration = MutableLiveData<Boolean>(false)

    private var exerciseId = MutableLiveData<UUID?>(null)
    val isNew: LiveData<Boolean> = exerciseId.map { it != null }

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    //Events
    val snackbarMessageEvent = SingleLiveEvent<Int>()
    val saveCompletedEvent = SingleLiveEvent<UUID>()

    private var exercise = Exercise()
    private var isDataLoaded: Boolean = false

    fun init(id: UUID?) {
        if (isDataLoaded)
            return
        isDataLoaded = true
        exerciseId.value = id
        id?.let {
            loadExercise(it)
        }
    }

    private fun loadExercise(exerciseId: UUID) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            when (val result = exerciseRepo.getExerciseById(exerciseId)) {
                is Result.Success -> exercise = result.data
                is Result.Error -> snackbarMessageEvent.value = R.string.could_not_load_data
                else -> Unit
            }

            name.value = exercise.name
            description.value = exercise.description
            hasDuration.value = exercise.hasDuration

            _isLoading.value = false
        }
    }

    fun saveExercise() {
        if (name.value.isNullOrEmpty()) {
            snackbarMessageEvent.value = R.string.exercise_name_is_required
            return
        }

        exercise.name = name.value!!
        exercise.description = description.value
        exercise.hasDuration = hasDuration.value!!

        viewModelScope.launch {
            _isLoading.value = true
            exerciseRepo.upsertExercise(exercise)
            saveCompletedEvent.value = exercise.id
        }
    }
}