package ru.sweetmilk.gymtracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet
import java.util.UUID

@Dao
interface TrainingPlanDao {

    @Query("SELECT * FROM EXERCISES")
    fun getTrainingPlanObservable(): LiveData<List<TrainingPlanExercise>>

    @Query("SELECT * FROM EXERCISES")
    suspend fun getTrainingPlan(): List<TrainingPlanExercise>

    @Query("DELETE FROM TRAINING_PLAN_SETS")
    suspend fun deleteAllTrainingPlanSets()

    @Query("DELETE FROM TRAINING_PLAN_SETS where exercise_id = :exerciseId")
    suspend fun deleteTrainingPlanSetsByExerciseId(exerciseId: UUID)

   @Upsert
    suspend fun upsertAllTrainingPlanSets(items: List<TrainingPlanSet>)
}