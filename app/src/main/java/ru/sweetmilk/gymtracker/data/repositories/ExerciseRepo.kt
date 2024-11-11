package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.withContext
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.entities.Exercise
import java.lang.NullPointerException
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExerciseRepo @Inject constructor(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) {
    fun getAllExercisesObservable(): LiveData<Result<List<Exercise>>> {
        return database.getExerciseDao().getAllExercisesObservable().map {
            Result.Success(it)
        }
    }

    fun getExerciseByIdObservable(id: UUID): LiveData<Result<Exercise>> {
        return database.getExerciseDao().getExerciseByIdObservable(id).map {
            Result.Success(it)
        }
    }

    suspend fun getExerciseById(id: UUID): Result<Exercise> = withContext(coroutineContext) {
        try {
            val exercise =
                database.getExerciseDao().getExerciseById(id) ?: throw NullPointerException()
            Result.Success(exercise)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun upsertExercise(exercise: Exercise) = withContext(coroutineContext) {
        database.getExerciseDao().upsertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) = withContext(coroutineContext) {
        exercise.isDeleted = true
        database.getExerciseDao().upsertExercise(exercise)
    }
}