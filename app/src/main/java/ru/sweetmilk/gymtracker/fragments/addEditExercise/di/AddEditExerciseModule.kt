package ru.sweetmilk.gymtracker.fragments.addEditExercise.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.di.ViewModelKey
import ru.sweetmilk.gymtracker.fragments.addEditExercise.AddEditExerciseViewModel

@Module
abstract class AddEditExerciseModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddEditExerciseViewModel::class)
    abstract fun bindViewModel(viewModel: AddEditExerciseViewModel): ViewModel
}