package ru.sweetmilk.gymtracker.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.gymtracker.data.di.DataModule
import ru.sweetmilk.gymtracker.cases.addEditExercise.di.AddEditExerciseComponent
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanExercise.di.AddEditTrainingPlanExerciseComponent
import ru.sweetmilk.gymtracker.cases.exerciseDetails.di.ExerciseDetailsComponent
import ru.sweetmilk.gymtracker.cases.exercises.di.ExercisesComponent
import ru.sweetmilk.gymtracker.cases.trainingPlan.di.TrainingPlanComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        ViewModelBuilderModule::class,
        FragmentSubcomponentsModule::class,
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
}

@Module(
    subcomponents = [
        ExercisesComponent::class,
        AddEditExerciseComponent::class,
        ExerciseDetailsComponent::class,
        TrainingPlanComponent::class,
        AddEditTrainingPlanExerciseComponent::class
    ]
)
object FragmentSubcomponentsModule