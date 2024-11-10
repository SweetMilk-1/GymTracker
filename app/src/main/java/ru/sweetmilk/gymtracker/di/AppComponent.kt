package ru.sweetmilk.gymtracker.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.gymtracker.data.di.DatabaseModule
import ru.sweetmilk.gymtracker.ui.exercises.di.ExercisesComponent
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
}

@Module(
    subcomponents = [
        ExercisesComponent::class
    ]
)
object FragmentSubcomponentsModule