package ru.sweetmilk.gymtracker.cases.exercises.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.cases.exercises.ExercisesFragment

@Subcomponent(modules = [ExercisesModule::class])
interface ExercisesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }

    fun inject(fragment: ExercisesFragment)
}