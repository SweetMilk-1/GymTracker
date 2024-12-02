package ru.sweetmilk.gymtracker.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.withContext
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.entities.Training
import ru.sweetmilk.gymtracker.data.repositories.TrainingRepo
import kotlin.coroutines.CoroutineContext
import ru.sweetmilk.gymtracker.data.Result

class TrainingRepoImpl(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingRepo {
    override fun getAllTrainingsObservable(): LiveData<Result<List<Training>>> {
        return database.getTrainingDao().getAllTrainingsObservable().map {
            Result.Success(it)
        }
    }

    override suspend fun getAllTrainings(): Result<List<Training>> = withContext(coroutineContext) {
        try {
            Result.Success(database.getTrainingDao().getAllTrainings())
        }
        catch(e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun upsertTraining(training: Training) = withContext(coroutineContext) {
        database.getTrainingDao().upsertTraining(training)
    }

    override suspend fun deleteTraining(training: Training) = withContext(coroutineContext) {
        database.getTrainingDao().deleteTraining(training)
    }
}