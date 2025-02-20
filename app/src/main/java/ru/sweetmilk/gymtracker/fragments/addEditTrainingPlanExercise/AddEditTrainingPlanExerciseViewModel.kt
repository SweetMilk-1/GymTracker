package ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import java.util.UUID
import javax.inject.Inject

class AddEditTrainingPlanExerciseViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
) : ViewModel() {

    //LiveData
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> get() = _exercises

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _hasDuration = MutableLiveData(false)
    val hasDuration: LiveData<Boolean> get() = _hasDuration

    private val _trainingPlanSets = MutableLiveData<List<TrainingPlanSet>>(listOf())
    val trainingPlanSets: LiveData<List<TrainingPlanSet>> get() = _trainingPlanSets

    val isNotEmpty: LiveData<Boolean> = trainingPlanSets.map { it.isNotEmpty() }

    private var selectedExercise: Exercise? = null

    //Events
    val snackbarMessageEvent = SingleLiveEvent<Int>()
    val initExerciseEvent = SingleLiveEvent<Int>()
    val saveTrainingPlanItems = SingleLiveEvent<TrainingPlanExercise>()

    private var viewModelInitialized = false

    fun init(
        trainingPlanExercise: TrainingPlanExercise?,
        excludedExerciseIds: List<UUID>?
    ) {
        if (viewModelInitialized)
            return

        viewModelInitialized = true

        _isLoading.value = true
        viewModelScope.launch {
            val excludedExerciseIdsList = excludedExerciseIds?.toMutableList()
                ?.filter { it != trainingPlanExercise?.exercise?.id }

            when (val response = exerciseRepo.getAllExercises(excludedExerciseIdsList)) {
                is Result.Success -> _exercises.value = response.data
                else -> snackbarMessageEvent.value = R.string.could_not_load_data
            }
            _isLoading.value = false

            if (trainingPlanExercise != null) {
                _trainingPlanSets.value = trainingPlanExercise.trainingPlanSets
                selectExercise(trainingPlanExercise.exercise)

                val exercisePosition =
                    exercises.value?.indexOfFirst { it.id == selectedExercise?.id }
                if (exercisePosition != null)
                    initExerciseEvent.value = exercisePosition
            }
        }
    }

    fun selectExercise(position: Int) {
        val newSelectedExercise = exercises.value?.get(position) ?: return
        if (hasDuration.value != newSelectedExercise.hasDuration) {
            _trainingPlanSets.value = listOf()
        }
        selectExercise(newSelectedExercise)
    }

    private fun selectExercise(exercise: Exercise) {
        selectedExercise = exercise
        _hasDuration.value = exercise.hasDuration
    }

    fun addNewTrainingPlanItem() {
        if (selectedExercise == null) {
            snackbarMessageEvent.value = R.string.choose_exercise
            return
        }
        val list = trainingPlanSets.value?.toMutableList() ?: return
        list.add(TrainingPlanSet(selectedExercise!!.id))
        _trainingPlanSets.value = list
    }

    fun removeTrainingPlanItem(id: UUID) {
        val list = trainingPlanSets.value?.toMutableList() ?: return
        _trainingPlanSets.value = list.filter { it.id != id }
    }

    fun updateTrainingPlanItem(item: TrainingPlanSet) {
        val list = trainingPlanSets.value?.toMutableList() ?: return
        val itemPosition = list.indexOfFirst { it.id == item.id }
        list[itemPosition] = item
        _trainingPlanSets.value = list
    }

    fun onSave() {
        if (trainingPlanSets.value?.isEmpty() != true && selectedExercise != null) {
            saveTrainingPlanItems.value =
                TrainingPlanExercise(selectedExercise!!, trainingPlanSets.value!!)
        }
    }
}