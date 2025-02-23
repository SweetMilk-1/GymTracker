package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet

interface TrainingPlanRepo {
    fun getTrainingPlanObservable(): LiveData<Result<List<TrainingPlanExercise>>>

    suspend fun getTrainingPlan(): Result<List<TrainingPlanExercise>>

    suspend fun updateTrainingPlan(list: List<TrainingPlanSet>)

    suspend fun deleteTrainingPlanExercise(item: TrainingPlanExercise)
}