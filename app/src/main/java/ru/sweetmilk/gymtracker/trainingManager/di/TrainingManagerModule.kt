package ru.sweetmilk.gymtracker.trainingManager.di

import dagger.Module
import dagger.Provides
import ru.sweetmilk.gymtracker.data.repositories.TrainingPlanRepo
import ru.sweetmilk.gymtracker.data.sharedpref.TrainingStatePref
import ru.sweetmilk.gymtracker.trainingManager.TrainingManager
import ru.sweetmilk.gymtracker.trainingManager.TrainingManagerImpl
import kotlin.coroutines.CoroutineContext

@Module
class TrainingManagerModule {
    @Provides
    fun provideTrainingManager(
        trainingStatePref: TrainingStatePref,
        coroutineContext: CoroutineContext,
        trainingPlanRepo: TrainingPlanRepo
    ): TrainingManager {
        return TrainingManagerImpl(
            trainingStatePref,
            coroutineContext,
            trainingPlanRepo
        )
    }
}