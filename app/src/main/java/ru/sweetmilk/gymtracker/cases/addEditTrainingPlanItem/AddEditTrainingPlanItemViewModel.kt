package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import java.util.UUID
import javax.inject.Inject

class AddEditTrainingPlanItemViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
) : ViewModel() {

    //LiveData
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> get() = _exercises

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _hasDuration = MutableLiveData(false)
    val hasDuration: LiveData<Boolean> get() = _hasDuration

    private val _trainingPlanItems = MutableLiveData<List<TrainingPlanItem>>(listOf())
    val trainingPlanItems: LiveData<List<TrainingPlanItem>> get() = _trainingPlanItems

    private var selectedExercise: Exercise? = null

    //Events
    val snackbarMessageEvent = SingleLiveEvent<Int>()

    private var viewModelInitialized = false

    fun init() {
        if (viewModelInitialized) return
        viewModelInitialized = true
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            when (val response = exerciseRepo.getAllExercises()) {
                is Result.Success -> _exercises.value = response.data
                else -> snackbarMessageEvent.value = R.string.could_not_load_data
            }
            _isLoading.value = false
        }
    }

    fun selectExercise(position: Int) {
        val newSelectedExercise = exercises.value?.get(position)
        if (hasDuration.value != newSelectedExercise?.hasDuration) {
            _trainingPlanItems.value = listOf()
        }
        selectedExercise = newSelectedExercise
        _hasDuration.value = selectedExercise?.hasDuration ?: false
    }

    fun addNewTrainingPlanItem() {
        if (selectedExercise == null) {
            snackbarMessageEvent.value = R.string.choose_exercise
            return
        }
        val list = trainingPlanItems.value?.toMutableList() ?: return
        list.add(TrainingPlanItem(selectedExercise!!.id))
        _trainingPlanItems.value = list
    }

    fun removeTrainingPlanItem(id: UUID) {
        val list = trainingPlanItems.value?.toMutableList() ?: return
        _trainingPlanItems.value = list.filter { it.id != id }
    }

    fun updateTrainingPlanItem(item: TrainingPlanItem) {
        val list = trainingPlanItems.value?.toMutableList() ?: return
        val itemPosition = list.indexOfFirst { it.id == item.id }
        list[itemPosition] = item
        _trainingPlanItems.value = list
    }
}