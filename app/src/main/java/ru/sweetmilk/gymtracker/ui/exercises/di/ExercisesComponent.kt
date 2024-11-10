package ru.sweetmilk.gymtracker.ui.exercises.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.ui.exercises.ExercisesFragment

@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }

    fun inject(fragment: ExercisesFragment)
}