package ru.sweetmilk.gymtracker.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.withContext
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import kotlin.coroutines.CoroutineContext
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet

class TrainingPlanRepoImpl(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingPlanRepo {
    override fun getTrainingPlanObservable(): LiveData<Result<List<TrainingPlanExercise>>> =
        database.getTrainingPlanDao().getTrainingPlanObservable().map {
            Result.Success(filterTrainingPlanItems(it))
        }

    override suspend fun getTrainingPlan(): Result<List<TrainingPlanExercise>> =
        withContext(coroutineContext) {
            try {
                val trainingPlanExercises =
                    filterTrainingPlanItems(database.getTrainingPlanDao().getTrainingPlan())
                Result.Success(trainingPlanExercises)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    private fun filterTrainingPlanItems(list: List<TrainingPlanExercise>): List<TrainingPlanExercise> {
        return list.filter {
            it.trainingPlanSets.isNotEmpty()
        }.onEach {
            it.trainingPlanSets = it.trainingPlanSets.sortedBy {
                it.sortNumber
            }
        }.sortedBy {
            it.trainingPlanSets[0].sortNumber
        }
    }

    override suspend fun updateTrainingPlan(list: List<TrainingPlanSet>) =
        withContext(coroutineContext) {
            for ((index, value) in list.withIndex()) {
                value.sortNumber = index + 1
            }
            database.getTrainingPlanDao().deleteUnusedTrainingPlanSets(list.map { it.id })
            database.getTrainingPlanDao().upsertTrainingPlanSets(list)
        }

    override suspend fun deleteTrainingPlanExercise(item: TrainingPlanExercise) =
        withContext(coroutineContext) {
            database.getTrainingPlanDao().deleteTrainingPlanSetsByExerciseId(item.exercise.id)
        }
}