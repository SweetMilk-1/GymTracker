package ru.sweetmilk.gymtracker.ui.exercises

import androidx.lifecycle.ViewModel
import ru.sweetmilk.gymtracker.data.ExerciseRepo
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepo
): ViewModel() {

}