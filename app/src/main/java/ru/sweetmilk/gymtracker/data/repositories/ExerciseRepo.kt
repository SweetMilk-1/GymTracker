package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.Exercise
import java.util.UUID

interface ExerciseRepo {
    fun getAllExercisesObservable(): LiveData<Result<List<Exercise>>>

    fun getExerciseByIdObservable(id: UUID): LiveData<Result<Exercise>>

    suspend fun getExerciseById(id: UUID): Result<Exercise>

    suspend fun getAllExercises(): Result<List<Exercise>>

    suspend fun upsertExercise(exercise: Exercise)

    suspend fun deleteExercise(exercise: Exercise)
}