package ru.sweetmilk.gymtracker.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import kotlin.coroutines.CoroutineContext

class TrainingPlanRepoImpl constructor(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingPlanRepo {
    override fun getTrainingPlanObservable(): LiveData<Result<List<ExerciseAndTrainingPlanItems>>> =
        database.getTrainingPlanItemDao().getTrainingPlanObservable().map {
            Result.success(it)
        }
}