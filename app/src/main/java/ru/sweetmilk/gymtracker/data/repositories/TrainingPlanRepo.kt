package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems

interface TrainingPlanRepo {
    fun getTrainingPlanObservable(): LiveData<Result<List<ExerciseAndTrainingPlanItems>>>
}