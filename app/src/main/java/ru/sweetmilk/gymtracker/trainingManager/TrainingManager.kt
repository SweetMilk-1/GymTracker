package ru.sweetmilk.gymtracker.trainingManager

import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise

interface TrainingManager {
    suspend fun startTraining()
    suspend fun isTrainingNow() : Boolean
    suspend fun isRelaxNow() : Boolean
    suspend fun getCurrentTrainingPlanExercise(): TrainingPlanExercise?
    suspend fun finishTrainingPlanSetAndGoNext() //TODO: добавить данные о подходе
}