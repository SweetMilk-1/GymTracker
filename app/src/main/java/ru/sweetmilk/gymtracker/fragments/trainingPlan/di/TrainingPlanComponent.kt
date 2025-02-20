package ru.sweetmilk.gymtracker.fragments.trainingPlan.di

import dagger.Subcomponent
import ru.sweetmilk.gymtracker.fragments.trainingPlan.TrainingPlanFragment

@Subcomponent(modules = [TrainingPlanModule::class])
interface TrainingPlanComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : TrainingPlanComponent
    }

    fun inject(fragment: TrainingPlanFragment)
}