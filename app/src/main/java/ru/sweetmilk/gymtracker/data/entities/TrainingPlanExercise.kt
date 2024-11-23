package ru.sweetmilk.gymtracker.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class TrainingPlanExercise (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id"
    )
    var trainingPlanSets: List<TrainingPlanSet>
) : Serializable