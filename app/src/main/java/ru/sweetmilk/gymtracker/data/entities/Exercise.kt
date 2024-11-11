package ru.sweetmilk.gymtracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "exercises",
)
data class Exercise (
    var name: String = "",
    var description: String? = null,
    @ColumnInfo("has_duration") var hasDuration: Boolean = false,
    @PrimaryKey val id: UUID = UUID.randomUUID()
)