package ru.sweetmilk.gymtracker.fragments.addEditExercise.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.addEditExercise.AddEditExerciseFragment

@Subcomponent(modules = [
    AddEditExerciseModule::class
])
interface AddEditExerciseComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : AddEditExerciseComponent
    }

    fun inject(fragment: AddEditExerciseFragment)
}