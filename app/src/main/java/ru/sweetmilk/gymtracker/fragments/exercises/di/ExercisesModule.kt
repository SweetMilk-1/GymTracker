package ru.sweetmilk.gymtracker.fragments.exercises.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.di.ViewModelKey
import ru.sweetmilk.gymtracker.fragments.exercises.ExercisesViewModel

@Module
abstract class ExercisesModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    abstract fun bindViewModel(viewmodel: ExercisesViewModel): ViewModel
}