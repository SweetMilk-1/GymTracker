package ru.sweetmilk.gymtracker.data.sharedpref

import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise

interface TrainingStatePref {
    var trainingPlan: List<TrainingPlanExercise>?
    var currentExerciseIndex: Int?
    var isRelaxNow: Boolean
}