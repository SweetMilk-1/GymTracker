package ru.sweetmilk.gymtracker.cases.trainingPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.SingleLiveEvent
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import javax.inject.Inject

class TrainingPlanViewModel @Inject constructor(
    private val trainingPlanRepo: TrainingPlanRepo
) : ViewModel() {
    //LiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val trainingPlanExercises: LiveData<Result<List<TrainingPlanExercise>>> =
        trainingPlanRepo.getTrainingPlanObservable()

    val isEmpty: LiveData<Boolean> = trainingPlanExercises.map {
        when (it) {
            is Result.Success -> it.data.isEmpty()
            else -> false
        }
    }

    //Events
    val createNewTrainingPlanExerciseEvent = SingleLiveEvent<Unit>()
    val updateTrainingPlanExerciseEvent = SingleLiveEvent<TrainingPlanExercise>()
    val snackbarMessageEvent = SingleLiveEvent<Int>()

    private var viewModelInitialized = false

    fun init(trainingPlanExercise: TrainingPlanExercise?) {
        if (viewModelInitialized)
            return
        viewModelInitialized = true

        if (trainingPlanExercise != null) {
            _isLoading.value = true
            viewModelScope.launch {
                when (val trainingPlanResult = trainingPlanRepo.getTrainingPlan()) {
                    is Result.Success -> updateTrainingPlan(
                        trainingPlanResult.data,
                        trainingPlanExercise
                    )

                    else -> snackbarMessageEvent.value = R.string.could_not_load_data
                }
                _isLoading.value = false
            }
        }
    }

    private suspend fun updateTrainingPlan(
        data: List<TrainingPlanExercise>,
        trainingPlanExercise: TrainingPlanExercise
    ) {
        val index = data.indexOfFirst { it.exercise.id == trainingPlanExercise.exercise.id }

        val updatedList = if (index == -1) {
            data.toMutableList().apply {
                add(trainingPlanExercise)
            }
        } else {
            data.toMutableList().apply {
                this[index] = trainingPlanExercise
            }
        }
        val trainingPlanSets = mutableListOf<TrainingPlanSet>()
        for (item in updatedList) {
            trainingPlanSets.addAll(item.trainingPlanSets)
        }

        trainingPlanRepo.upsertTrainingPlanItems(trainingPlanSets)
    }

    fun createNewTrainingPlanItem() {
        createNewTrainingPlanExerciseEvent.value = Unit
    }

    fun updateTrainingPlanItem(item: TrainingPlanExercise) {
        updateTrainingPlanExerciseEvent.value = item
    }

    fun getUsingExerciseIds(): List<String>? {
        return if (trainingPlanExercises.value is Result.Success) {
            (trainingPlanExercises.value as? Result.Success)?.data?.map { it.exercise.id.toString() }
        } else null
    }
}