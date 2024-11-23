package ru.sweetmilk.gymtracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "training_plan_sets",
    foreignKeys = [
        ForeignKey(Exercise::class, parentColumns = ["id"], childColumns = ["exercise_id"])
    ]
)
data class TrainingPlanSet(
    @ColumnInfo(name = "exercise_id") var exerciseId: UUID,

    var weight: Float? = null,
    var count: Int? = null,
    var duration: Int? = null,

    @ColumnInfo(name = "sort_number") var sortNumber: Int = 0,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
)