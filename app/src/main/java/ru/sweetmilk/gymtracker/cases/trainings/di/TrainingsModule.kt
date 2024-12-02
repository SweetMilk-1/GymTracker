package ru.sweetmilk.gymtracker.cases.trainings.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.cases.trainings.TrainingsViewModel
import ru.sweetmilk.gymtracker.di.ViewModelKey

@Module
abstract class TrainingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrainingsViewModel::class)
    abstract fun bindViewModel(viewmodel: TrainingsViewModel): ViewModel
}