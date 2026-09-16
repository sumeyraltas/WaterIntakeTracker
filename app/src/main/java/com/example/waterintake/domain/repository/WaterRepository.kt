package com.example.waterintake.domain.repository

import com.example.waterintake.domain.model.UserProfile
import com.example.waterintake.domain.model.WaterLog
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    fun getTodayLogs(dateString: String): Flow<List<WaterLog>>
    fun getTodayTotalIntake(dateString: String): Flow<Int>
    suspend fun addWaterIntake(amountMl: Int, containerType: String = "glass"): Long
    suspend fun deleteWaterLog(id: Long)
    suspend fun undoLastLog(): WaterLog?
    fun getUserProfile(): Flow<UserProfile>
    suspend fun updateUserProfile(profile: UserProfile)
    fun getHistorySummaries(limitDays: Int = 30): Flow<Map<String, Int>>
}
