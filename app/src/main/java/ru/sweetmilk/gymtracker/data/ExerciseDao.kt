package ru.sweetmilk.gymtracker.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import java.util.UUID

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM EXERCISES where name like '%' + :search + '%'")
    suspend fun getAllExercises(search: String = ""): List<Exercise>

    @Query("SELECT * FROM EXERCISES WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: UUID): Exercise?

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)
}