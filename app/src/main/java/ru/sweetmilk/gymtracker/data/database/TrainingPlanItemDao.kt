package ru.sweetmilk.gymtracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems

@Dao
interface TrainingPlanItemDao {

    @Query("SELECT * FROM TRAINING_PLAN_ITEMS TPI JOIN EXERCISES E on TPI.exercise_id=E.id")
    fun getTrainingPlanObservable(): LiveData<List<ExerciseAndTrainingPlanItems>>

}