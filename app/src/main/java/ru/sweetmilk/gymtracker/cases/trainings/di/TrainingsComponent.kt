package ru.sweetmilk.gymtracker.cases.trainings.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.cases.trainings.TrainingsFragment

@Subcomponent(modules = [TrainingsModule::class])
interface TrainingsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TrainingsComponent
    }

    fun inject(fragment: TrainingsFragment)
}