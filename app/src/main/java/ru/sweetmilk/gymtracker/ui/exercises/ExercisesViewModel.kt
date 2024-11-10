package ru.sweetmilk.gymtracker.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Exercise
import ru.sweetmilk.gymtracker.data.ExerciseRepo
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
) : ViewModel() {
    //LiveData
    private var _items = MutableLiveData<List<Exercise>>()
    val items: LiveData<List<Exercise>> get() = _items

    val isEmpty: LiveData<Boolean> = items.map {
        it.isEmpty()
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    //Events
    val createNewExerciseEvent = SingleLiveEvent<Unit>()
    val showSnackbarEvent = SingleLiveEvent<Int>()

    init {
        loadExercises(false)
    }

    fun updateExercises() {
        loadExercises(true)
    }

    fun createNewExercise() {
        createNewExerciseEvent.value = Unit
    }

    private fun loadExercises(isForceLoading: Boolean) {
        _isLoading.value = !isForceLoading
        viewModelScope.launch {
            delay(3000)
            _items.value = exerciseRepo.getAllExercises()
            _isLoading.value = false
        }
    }
}