package ru.sweetmilk.gymtracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem

@Dao
interface TrainingPlanItemDao {

    @Query("SELECT * FROM TRAINING_PLAN_ITEMS TPI JOIN EXERCISES E on TPI.exercise_id=E.id ORDER BY TPI.sort_number")
    fun getTrainingPlanObservable(): LiveData<List<ExerciseAndTrainingPlanItems>>

    @Query("SELECT * FROM TRAINING_PLAN_ITEMS TPI JOIN EXERCISES E on TPI.exercise_id=E.id ORDER BY TPI.sort_number")
    suspend fun getTrainingPlan(): List<ExerciseAndTrainingPlanItems>

    @Query("DELETE FROM TRAINING_PLAN_ITEMS")
    suspend fun deleteAllTrainingPlanItems()

   @Upsert
    suspend fun upsertAllTrainingPlanItems(items: List<TrainingPlanItem>)
}