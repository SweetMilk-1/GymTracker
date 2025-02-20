package ru.sweetmilk.gymtracker.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.gymtracker.data.di.DataModule
import ru.sweetmilk.gymtracker.fragments.addEditExercise.di.AddEditExerciseComponent
import ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise.di.AddEditTrainingPlanExerciseComponent
import ru.sweetmilk.gymtracker.fragments.exerciseDetails.di.ExerciseDetailsComponent
import ru.sweetmilk.gymtracker.fragments.exercises.di.ExercisesComponent
import ru.sweetmilk.gymtracker.fragments.trainingPlan.di.TrainingPlanComponent
import ru.sweetmilk.gymtracker.fragments.trainings.di.TrainingsComponent
import ru.sweetmilk.gymtracker.trainingManager.TrainingManager
import ru.sweetmilk.gymtracker.trainingManager.di.TrainingManagerModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        ViewModelBuilderModule::class,
        FragmentSubcomponentsModule::class,
        TrainingManagerModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun addExercisesComponent(): ExercisesComponent.Factory
    fun addAddEditExerciseComponent(): AddEditExerciseComponent.Factory
    fun addExerciseDetailsComponent(): ExerciseDetailsComponent.Factory
    fun addTrainingPlanComponent(): TrainingPlanComponent.Factory
    fun addAddEditTrainingPlanExerciseComponent(): AddEditTrainingPlanExerciseComponent.Factory
    fun addTrainingsComponent(): TrainingsComponent.Factory
}

@Module(
    subcomponents = [
        ExercisesComponent::class,
        AddEditExerciseComponent::class,
        ExerciseDetailsComponent::class,
        TrainingPlanComponent::class,
        AddEditTrainingPlanExerciseComponent::class,
        TrainingsComponent::class
    ]
)
object FragmentSubcomponentsModule