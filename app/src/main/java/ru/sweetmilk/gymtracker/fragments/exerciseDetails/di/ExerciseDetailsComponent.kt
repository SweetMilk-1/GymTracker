package ru.sweetmilk.gymtracker.fragments.exerciseDetails.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.exerciseDetails.ExerciseDetailsFragment

@Subcomponent(modules = [
    ExerciseDetailsModule::class
])
interface ExerciseDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExerciseDetailsComponent
    }

    fun inject(fragment: ExerciseDetailsFragment)
}