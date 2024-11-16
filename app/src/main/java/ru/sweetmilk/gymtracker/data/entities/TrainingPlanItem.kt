package ru.sweetmilk.gymtracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "training_plan_items",
    foreignKeys = [
        ForeignKey(Exercise::class, parentColumns = ["id"], childColumns = ["exercise_id"])
    ]
)
data class TrainingPlanItem(
    @ColumnInfo(name = "exercise_id") var exerciseId: UUID,

    var weight: Int = 0,
    var count: Int = 0,
    var duration: Int = 0,

    @ColumnInfo(name = "sort_number") var sortNumber: Int = 0,
    @PrimaryKey var id: UUID = UUID.randomUUID(),
)