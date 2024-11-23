package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanExercise.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanExercise.AddEditTrainingPlanExerciseFragment

@Subcomponent(modules = [AddEditTrainingPlanExerciseModule::class])
interface AddEditTrainingPlanExerciseComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : AddEditTrainingPlanExerciseComponent
    }

    fun inject(fragment: AddEditTrainingPlanExerciseFragment)
}