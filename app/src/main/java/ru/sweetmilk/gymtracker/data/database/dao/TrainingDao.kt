package ru.sweetmilk.gymtracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.sweetmilk.gymtracker.data.entities.Training

@Dao
interface TrainingDao {
    @Query("SELECT * FROM TRAININGS ORDER BY DATE DESC")
    fun getAllTrainingsObservable(): LiveData<List<Training>>
}