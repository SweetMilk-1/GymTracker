package ru.sweetmilk.gymtracker.fragments.trainingPlan

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
                    is Result.Success -> upsertTrainingPlan(
                        trainingPlanResult.data,
                        trainingPlanExercise
                    )

                    else -> snackbarMessageEvent.value = R.string.could_not_load_data
                }
                _isLoading.value = false
            }
        }
    }

    private suspend fun upsertTrainingPlan(
        oldTrainingPlanExercises: List<TrainingPlanExercise>,
        upsertedTrainingPlanExercise: TrainingPlanExercise
    ) {
        val index =
            oldTrainingPlanExercises.indexOfFirst { it.exercise.id == upsertedTrainingPlanExercise.exercise.id }

        val updatedList = if (index == -1) {
            oldTrainingPlanExercises.toMutableList().apply {
                add(upsertedTrainingPlanExercise)
            }
        } else {
            oldTrainingPlanExercises.toMutableList().apply {
                this[index] = upsertedTrainingPlanExercise
            }
        }
        updateTrainingPlan(updatedList)

        snackbarMessageEvent.value = R.string.exercise_saved
    }

    private suspend fun updateTrainingPlan(updatedList: MutableList<TrainingPlanExercise>) {
        val trainingPlanSets = mutableListOf<TrainingPlanSet>()
        for (item in updatedList) {
            trainingPlanSets.addAll(item.trainingPlanSets)
        }

        trainingPlanRepo.updateTrainingPlan(trainingPlanSets)
    }

    fun createNewTrainingPlanExercise() {
        createNewTrainingPlanExerciseEvent.value = Unit
    }

    fun updateTrainingPlanExercise(item: TrainingPlanExercise) {
        updateTrainingPlanExerciseEvent.value = item
    }

    fun deleteTrainingPlanExercise(item: TrainingPlanExercise) {
        viewModelScope.launch {
            trainingPlanRepo.deleteTrainingPlanExercise(item)
        }
        snackbarMessageEvent.value = R.string.delete_exercise_completed
    }

    fun moveUpTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) {
        viewModelScope.launch {
            val result = trainingPlanRepo.getTrainingPlan()

            if (result is Result.Success) {
                val list = result.data
                val index = list.indexOfFirst { it.exercise.id == trainingPlanExercise.exercise.id }
                if (index == 0)
                    return@launch

                val updatedList = list.toMutableList().apply {
                    val temp = this[index]
                    this[index] = this[index - 1]
                    this[index - 1] = temp
                }

                updateTrainingPlan(updatedList)
            }
        }
    }

    fun moveDownTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) {
        viewModelScope.launch {
            val result = trainingPlanRepo.getTrainingPlan()

            if (result is Result.Success) {
                val list = result.data
                val index = list.indexOfFirst { it.exercise.id == trainingPlanExercise.exercise.id }
                if (index == list.size - 1)
                    return@launch

                val updatedList = list.toMutableList().apply {
                    val temp = this[index]
                    this[index] = this[index + 1]
                    this[index + 1] = temp
                }

                updateTrainingPlan(updatedList)
            }
        }
    }

    fun getUsedExerciseIds(): List<String>? {
        return if (trainingPlanExercises.value is Result.Success) {
            (trainingPlanExercises.value as? Result.Success)?.data?.map { it.exercise.id.toString() }
        } else null
    }
}