package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem.AddEditTrainingPlanItemViewModel
import ru.sweetmilk.gymtracker.di.ViewModelKey

@Module
abstract class AddEditTrainingPlanItemModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEditTrainingPlanItemViewModel::class)
    abstract fun bindViewModel(viewModel: AddEditTrainingPlanItemViewModel): ViewModel
}