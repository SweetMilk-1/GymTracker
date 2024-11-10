package ru.sweetmilk.gymtracker.data

import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExerciseRepo @Inject constructor(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) {
    suspend fun getAllExercises(search: String): List<Exercise> = withContext(coroutineContext) {
        database.getExerciseDao().getAllExercises(search)
    }
}