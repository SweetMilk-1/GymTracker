package ru.sweetmilk.gymtracker.cases.trainingPlan.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.cases.trainingPlan.TrainingPlanViewModel
import ru.sweetmilk.gymtracker.di.ViewModelKey

@Module
abstract class TrainingPlanModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrainingPlanViewModel::class)
    abstract fun bindViewModel(viewModel: TrainingPlanViewModel): ViewModel
}