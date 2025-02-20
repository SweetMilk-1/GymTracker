package ru.sweetmilk.gymtracker.fragments.trainings.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.trainings.TrainingsFragment

@Subcomponent(modules = [TrainingsModule::class])
interface TrainingsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TrainingsComponent
    }

    fun inject(fragment: TrainingsFragment)
}