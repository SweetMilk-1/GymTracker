package ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise.AddEditTrainingPlanExerciseFragment

@Subcomponent(modules = [AddEditTrainingPlanExerciseModule::class])
interface AddEditTrainingPlanExerciseComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : AddEditTrainingPlanExerciseComponent
    }

    fun inject(fragment: AddEditTrainingPlanExerciseFragment)
}