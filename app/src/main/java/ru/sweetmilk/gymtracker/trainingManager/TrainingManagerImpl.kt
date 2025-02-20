package ru.sweetmilk.gymtracker.trainingManager

import kotlinx.coroutines.withContext
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import ru.sweetmilk.gymtracker.data.sharedpref.TrainingStatePref
import ru.sweetmilk.gymtracker.data.succeeded
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TrainingManagerImpl @Inject constructor(
    private val trainingStatePref: TrainingStatePref,
    private val coroutineContext: CoroutineContext,
    private val trainingPlanRepo: TrainingPlanRepo
) : TrainingManager {
    override suspend fun startTraining(): Unit = withContext(coroutineContext) {
        val trainingPlanExercises = trainingPlanRepo.getTrainingPlan()
        if (trainingPlanExercises.succeeded) {
            trainingStatePref.apply {
                trainingPlan = (trainingPlanExercises as Result.Success).data
                currentExerciseIndex = 0
            }
        }
    }

    override suspend fun isTrainingNow(): Boolean = withContext(coroutineContext) {
        trainingStatePref.currentExerciseIndex != null
    }

    override suspend fun isRelaxNow(): Boolean = withContext(coroutineContext) {
        trainingStatePref.isRelaxNow
    }

    override suspend fun getCurrentTrainingPlanExercise(): TrainingPlanExercise? =
        withContext(coroutineContext) {
            if (trainingStatePref.currentExerciseIndex == null)
                null
            else
                trainingStatePref.trainingPlan?.get(trainingStatePref.currentExerciseIndex!!)
        }

    override suspend fun finishTrainingPlanSetAndGoNext(): Unit = withContext(coroutineContext) {
        trainingStatePref.apply {
            if (trainingPlan?.lastIndex == currentExerciseIndex ) {
                currentExerciseIndex = null
                trainingPlan = null
                return@withContext
            }
            currentExerciseIndex = currentExerciseIndex?.plus(1)
        }
    }
}