package ru.sweetmilk.gymtracker.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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
}