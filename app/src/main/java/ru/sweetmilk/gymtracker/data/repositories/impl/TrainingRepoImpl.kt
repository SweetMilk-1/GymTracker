package ru.sweetmilk.gymtracker.data.repositories.impl

import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.repositories.TrainingRepo
import kotlin.coroutines.CoroutineContext

class TrainingRepoImpl(
    private val database: AppDatabase,
    private val coroutineContext: CoroutineContext
) : TrainingRepo {
}