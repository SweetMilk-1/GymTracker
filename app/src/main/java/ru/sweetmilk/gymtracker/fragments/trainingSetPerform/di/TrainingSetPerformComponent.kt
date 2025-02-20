package ru.sweetmilk.gymtracker.fragments.trainingSetPerform.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.trainingSetPerform.TrainingSetPerformFragment
import ru.sweetmilk.gymtracker.fragments.trainings.TrainingsFragment
import ru.sweetmilk.gymtracker.fragments.trainings.di.TrainingsComponent
import ru.sweetmilk.gymtracker.fragments.trainings.di.TrainingsModule

@Subcomponent(modules = [TrainingSetPerformModule::class])
interface TrainingSetPerformComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): TrainingSetPerformComponent
    }

    fun inject(fragment: TrainingSetPerformFragment)
}