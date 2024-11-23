package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanExercise.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanExercise.AddEditTrainingPlanExerciseViewModel
import ru.sweetmilk.gymtracker.di.ViewModelKey

@Module
abstract class AddEditTrainingPlanExerciseModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEditTrainingPlanExerciseViewModel::class)
    abstract fun bindViewModel(viewModel: AddEditTrainingPlanExerciseViewModel): ViewModel
}