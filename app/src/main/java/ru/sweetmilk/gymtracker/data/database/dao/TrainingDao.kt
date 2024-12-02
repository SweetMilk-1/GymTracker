package ru.sweetmilk.gymtracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.sweetmilk.gymtracker.data.entities.Training

@Dao
interface TrainingDao {
    @Query("SELECT * FROM TRAININGS ORDER BY DATE DESC")
    fun getAllTrainingsObservable(): LiveData<List<Training>>

    @Query("SELECT * FROM TRAININGS ORDER BY DATE DESC")
    suspend fun getAllTrainings(): List<Training>

    @Upsert
    suspend fun upsertTraining(training: Training)

    @Delete
    suspend fun deleteTraining(training: Training)
}