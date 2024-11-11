package ru.sweetmilk.gymtracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.sweetmilk.gymtracker.data.entities.Exercise
import java.util.UUID

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM EXERCISES")
    fun getAllExercisesObservable(): LiveData<List<Exercise>>

    @Query("SELECT * FROM EXERCISES WHERE id = :exerciseId")
    fun getExerciseByIdObservable(exerciseId: UUID): LiveData<Exercise>

    @Query("SELECT * FROM EXERCISES WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: UUID): Exercise?

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)
}