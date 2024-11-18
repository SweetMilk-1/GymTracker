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
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import java.util.UUID
import javax.inject.Inject

class TrainingPlanViewModel @Inject constructor(
    private val trainingPlanRepo: TrainingPlanRepo
) : ViewModel() {
    //LiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val exercisesAndTrainingPlanItems: LiveData<Result<List<ExerciseAndTrainingPlanItems>>> =
        trainingPlanRepo.getTrainingPlanObservable()

    val isEmpty: LiveData<Boolean> = exercisesAndTrainingPlanItems.map {
        when (it) {
            is Result.Success -> it.data.isEmpty()
            else -> false
        }
    }

    //Events
    val createNewTrainingPlanItemEvent = SingleLiveEvent<Unit>()
    val updateTrainingPlanItemEvent = SingleLiveEvent<ExerciseAndTrainingPlanItems>()
    val snackbarMessageEvent = SingleLiveEvent<Int>()

    private var viewModelInitialized = false

    fun init(exerciseAndTrainingPlanItems: ExerciseAndTrainingPlanItems?) {
        if (viewModelInitialized)
            return
        viewModelInitialized = true

        if (exerciseAndTrainingPlanItems != null) {
            _isLoading.value = true
            viewModelScope.launch {
                when (val trainingPlanResult = trainingPlanRepo.getTrainingPlan()) {
                    is Result.Success -> updateTrainingPlan(
                        trainingPlanResult.data,
                        exerciseAndTrainingPlanItems
                    )

                    else -> snackbarMessageEvent.value = R.string.could_not_load_data
                }
                _isLoading.value = false
            }
        }
    }

    private suspend fun updateTrainingPlan(
        data: List<ExerciseAndTrainingPlanItems>,
        exerciseAndTrainingPlanItems: ExerciseAndTrainingPlanItems
    ) {
        val index = data.indexOfFirst { it.exercise.id == exerciseAndTrainingPlanItems.exercise.id }

        val updatedList = if (index == -1) {
            data.toMutableList().apply {
                add(exerciseAndTrainingPlanItems)
            }
        } else {
            data.toMutableList().apply {
                this[index] = exerciseAndTrainingPlanItems
            }
        }
        val trainingPlanItems = mutableListOf<TrainingPlanItem>()
        for (item in updatedList) {
            trainingPlanItems.addAll(item.trainingPlanItems)
        }

        trainingPlanRepo.upsertTrainingPlanItems(trainingPlanItems)
    }

    fun createNewTrainingPlanItem() {
        createNewTrainingPlanItemEvent.value = Unit
    }

    fun updateTrainingPlanItem(item: ExerciseAndTrainingPlanItems) {
        updateTrainingPlanItemEvent.value = item
    }

    fun getUsingExerciseIds(): List<String>? {
        return if (exercisesAndTrainingPlanItems.value is Result.Success) {
            (exercisesAndTrainingPlanItems.value as? Result.Success)?.data?.map { it.exercise.id.toString() }
        } else null
    }
}