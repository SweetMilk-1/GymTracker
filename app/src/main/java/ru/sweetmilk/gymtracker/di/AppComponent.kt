package ru.sweetmilk.gymtracker.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.gymtracker.data.di.DataModule
import ru.sweetmilk.gymtracker.cases.addEditExercise.di.AddEditExerciseComponent
import ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem.di.AddEditTrainingPlanItemComponent
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
    fun addAddEditTrainingPlanItemComponent(): AddEditTrainingPlanItemComponent.Factory
}

@Module(
    subcomponents = [
        ExercisesComponent::class,
        AddEditExerciseComponent::class,
        ExerciseDetailsComponent::class,
        TrainingPlanComponent::class,
        AddEditTrainingPlanItemComponent::class
    ]
)
object FragmentSubcomponentsModule