package ru.sweetmilk.gymtracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.sweetmilk.gymtracker.data.entities.Exercise
import java.util.UUID

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM EXERCISES where is_deleted=0")
    fun getAllExercisesObservable(): LiveData<List<Exercise>>

    @Query("SELECT * FROM EXERCISES WHERE id = :exerciseId")
    fun getExerciseByIdObservable(exerciseId: UUID): LiveData<Exercise>

    @Query("SELECT * FROM EXERCISES where is_deleted=0 AND id NOT IN (:uuids)")
    suspend fun getAllExercises(uuids: List<UUID>): List<Exercise>

    @Query("SELECT * FROM EXERCISES WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: UUID): Exercise?

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)
}