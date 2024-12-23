package ru.sweetmilk.gymtracker.cases.exercises.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.di.ViewModelKey
import ru.sweetmilk.gymtracker.cases.exercises.ExercisesViewModel

@Module
abstract class ExercisesModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    abstract fun bindViewModel(viewmodel: ExercisesViewModel): ViewModel
}