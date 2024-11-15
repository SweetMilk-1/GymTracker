package ru.sweetmilk.gymtracker.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.gymtracker.data.di.DatabaseModule
import ru.sweetmilk.gymtracker.cases.addEditExercise.di.AddEditExerciseComponent
import ru.sweetmilk.gymtracker.cases.exerciseDetails.di.ExerciseDetailsComponent
import ru.sweetmilk.gymtracker.cases.exercises.di.ExercisesComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
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
}

@Module(
    subcomponents = [
        ExercisesComponent::class,
        AddEditExerciseComponent::class,
        ExerciseDetailsComponent::class,
    ]
)
object FragmentSubcomponentsModule