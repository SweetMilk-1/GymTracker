package ru.sweetmilk.gymtracker.cases.exerciseDetails.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.cases.exerciseDetails.ExerciseDetailsViewModel
import ru.sweetmilk.gymtracker.di.ViewModelKey

@Module
abstract class ExerciseDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExerciseDetailsViewModel::class)
    abstract fun bindViewModel(viewModel: ExerciseDetailsViewModel): ViewModel
}