package com.example.waterintake.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: WaterIntakeEntity): Long

    @Delete
    suspend fun deleteLog(log: WaterIntakeEntity)

    @Query("DELETE FROM water_intake_logs WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM water_intake_logs WHERE id = (SELECT MAX(id) FROM water_intake_logs)")
    suspend fun getLastLog(): WaterIntakeEntity?

    @Query("SELECT * FROM water_intake_logs WHERE dateString = :dateString ORDER BY timestamp DESC")
    fun getLogsForDate(dateString: String): Flow<List<WaterIntakeEntity>>

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_intake_logs WHERE dateString = :dateString")
    fun getTotalIntakeForDate(dateString: String): Flow<Int>

    @Query("SELECT * FROM water_intake_logs WHERE dateString >= :startDate ORDER BY timestamp ASC")
    fun getLogsSinceDate(startDate: String): Flow<List<WaterIntakeEntity>>

    @Query("SELECT dateString, SUM(amountMl) as totalMl FROM water_intake_logs GROUP BY dateString ORDER BY dateString DESC LIMIT :limit")
    fun getDailySummaries(limit: Int = 30): Flow<List<DailySumTuple>>
}

data class DailySumTuple(
    val dateString: String,
    val totalMl: Int
)
