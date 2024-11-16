package ru.sweetmilk.gymtracker.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseAndTrainingPlanItems (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id"
    )
    val trainingPlanItems: List<TrainingPlanItem>
)