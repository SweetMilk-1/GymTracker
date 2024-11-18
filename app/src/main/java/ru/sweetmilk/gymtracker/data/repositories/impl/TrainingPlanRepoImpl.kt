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

class TrainingPlanRepoImpl constructor(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingPlanRepo {
    override fun getTrainingPlanObservable(): LiveData<Result<List<ExerciseAndTrainingPlanItems>>> =
        database.getTrainingPlanItemDao().getTrainingPlanObservable().map {
            Result.Success(it)
        }

    override suspend fun getTrainingPlan(): Result<List<ExerciseAndTrainingPlanItems>> =
        withContext(coroutineContext) {
            try {
                Result.Success(database.getTrainingPlanItemDao().getTrainingPlan())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTrainingPlanItems(list: List<TrainingPlanItem>) =
        withContext(coroutineContext) {
            for ((index, value) in list.withIndex()) {
                value.sortNumber = index + 1
            }
            database.getTrainingPlanItemDao().upsertAllTrainingPlanItems(list)
        }
}