package ru.sweetmilk.gymtracker.fragments.trainingSetPerform.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.di.ViewModelKey
import ru.sweetmilk.gymtracker.fragments.trainingSetPerform.TrainingSetPerformViewModel
import ru.sweetmilk.gymtracker.fragments.trainings.TrainingsViewModel

@Module
abstract class TrainingSetPerformModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrainingSetPerformViewModel::class)
    abstract fun bindViewModel(viewmodel: TrainingSetPerformViewModel): ViewModel
}