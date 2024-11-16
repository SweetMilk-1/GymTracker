package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem.AddEditTrainingPlanItemFragment
import ru.sweetmilk.gymtracker.cases.trainingPlan.TrainingPlanFragment

@Subcomponent(modules = [AddEditTrainingPlanItemModule::class])
interface AddEditTrainingPlanItemComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : AddEditTrainingPlanItemComponent
    }

    fun inject(fragment: AddEditTrainingPlanItemFragment)
}