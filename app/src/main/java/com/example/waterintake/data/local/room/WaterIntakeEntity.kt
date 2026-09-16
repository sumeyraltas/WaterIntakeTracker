package com.example.waterintake.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "water_intake_logs")
data class WaterIntakeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountMl: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val dateString: String = LocalDate.now().toString(), // YYYY-MM-DD
    val containerType: String = "glass" // glass, bottle, flask, custom
)
