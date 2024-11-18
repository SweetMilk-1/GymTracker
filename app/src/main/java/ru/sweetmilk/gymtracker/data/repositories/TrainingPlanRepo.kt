package ru.sweetmilk.gymtracker.data.repositories

import androidx.lifecycle.LiveData
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem

interface TrainingPlanRepo {
    fun getTrainingPlanObservable(): LiveData<Result<List<ExerciseAndTrainingPlanItems>>>

    suspend fun getTrainingPlan(): Result<List<ExerciseAndTrainingPlanItems>>

    suspend fun upsertTrainingPlanItems(list: List<TrainingPlanItem>)


}