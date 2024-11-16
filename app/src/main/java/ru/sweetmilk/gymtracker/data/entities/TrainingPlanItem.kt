package ru.sweetmilk.gymtracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "exercise_plan_items",
    foreignKeys = [
        ForeignKey(Exercise::class, parentColumns = ["id"], childColumns = ["exerciseId"])
    ]
)
data class TrainingPlanItem(
    @ColumnInfo(name = "exercise_id") var exerciseId: Int,

    var weight: Int = 0,
    var count: Int = 0,
    var duration: Int = 0,

    @ColumnInfo(name = "sort_number") var sortNumber: Int = 0,
    @PrimaryKey var id: UUID = UUID.randomUUID(),
)