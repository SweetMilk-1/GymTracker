package ru.sweetmilk.gymtracker.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import ru.sweetmilk.gymtracker.data.repositories.ExerciseRepo
import ru.sweetmilk.gymtracker.data.repositories.TrainingRepo
import ru.sweetmilk.gymtracker.data.repositories.impl.TrainingPlanRepoImpl
import ru.sweetmilk.gymtracker.data.repositories.impl.ExerciseRepoImpl
import ru.sweetmilk.gymtracker.data.repositories.impl.TrainingRepoImpl
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideExerciseRepo(
        database: AppDatabase,
        coroutineContext: CoroutineContext
    ): ExerciseRepo {
        return ExerciseRepoImpl(database, coroutineContext)
    }

    @Provides
    @Singleton
    fun provideTrainingPlanRepo(
        database: AppDatabase,
        coroutineContext: CoroutineContext
    ): TrainingPlanRepo {
        return TrainingPlanRepoImpl(database, coroutineContext)
    }

    @Provides
    @Singleton
    fun provideTrainingRepo(
        database: AppDatabase,
        coroutineContext: CoroutineContext
    ): TrainingRepo {
        return TrainingRepoImpl(database, coroutineContext)
    }
}