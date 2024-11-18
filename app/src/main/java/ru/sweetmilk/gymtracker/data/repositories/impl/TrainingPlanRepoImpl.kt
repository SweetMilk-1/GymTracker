package ru.sweetmilk.gymtracker.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.withContext
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import kotlin.coroutines.CoroutineContext
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem

class TrainingPlanRepoImpl(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingPlanRepo {
    override fun getTrainingPlanObservable(): LiveData<Result<List<ExerciseAndTrainingPlanItems>>> =
        database.getTrainingPlanItemDao().getTrainingPlanObservable().map {
            Result.Success(filterTrainingPlanItems(it))
        }

    override suspend fun getTrainingPlan(): Result<List<ExerciseAndTrainingPlanItems>> =
        withContext(coroutineContext) {
            try {
                val trainingPlanExercises =
                    filterTrainingPlanItems(database.getTrainingPlanItemDao().getTrainingPlan())
                Result.Success(trainingPlanExercises)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    private fun filterTrainingPlanItems(list: List<ExerciseAndTrainingPlanItems>): List<ExerciseAndTrainingPlanItems> {
        return list.filter {
            it.trainingPlanItems.isNotEmpty()
        }.onEach {
            it.trainingPlanItems = it.trainingPlanItems.sortedBy {
                it.sortNumber
            }
        }.sortedBy {
            it.trainingPlanItems[0].sortNumber
        }
    }

    override suspend fun upsertTrainingPlanItems(list: List<TrainingPlanItem>) =
        withContext(coroutineContext) {
            for ((index, value) in list.withIndex()) {
                value.sortNumber = index + 1
            }
            database.getTrainingPlanItemDao().deleteAllTrainingPlanItems()
            database.getTrainingPlanItemDao().upsertAllTrainingPlanItems(list)
        }
}