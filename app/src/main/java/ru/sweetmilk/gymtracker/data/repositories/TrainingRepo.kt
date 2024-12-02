package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import ru.sweetmilk.gymtracker.data.entities.Training
import ru.sweetmilk.gymtracker.data.Result

interface TrainingRepo {
    fun getAllTrainingsObservable(): LiveData<Result<List<Training>>>

    suspend fun getAllTrainings(): Result<List<Training>>

    suspend fun upsertTraining(training: Training)

    suspend fun deleteTraining(training: Training)
}