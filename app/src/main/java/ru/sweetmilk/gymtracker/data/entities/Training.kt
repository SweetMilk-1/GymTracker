package ru.sweetmilk.gymtracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date
import java.util.UUID

@Entity(tableName = "trainings")
data class Training(
    var grade: Int? = null,
    var summary: String? = null,
    var date: Date = Calendar.getInstance().time,
    @PrimaryKey var id: UUID = UUID.randomUUID()
)