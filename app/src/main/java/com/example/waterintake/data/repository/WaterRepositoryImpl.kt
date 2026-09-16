package com.example.waterintake.data.repository

import com.example.waterintake.data.datastore.UserPreferencesRepository
import com.example.waterintake.data.local.room.WaterDao
import com.example.waterintake.data.local.room.WaterIntakeEntity
import com.example.waterintake.domain.model.UserProfile
import com.example.waterintake.domain.model.WaterLog
import com.example.waterintake.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterRepositoryImpl @Inject constructor(
    private val waterDao: WaterDao,
    private val preferencesRepository: UserPreferencesRepository
) : WaterRepository {

    override fun getTodayLogs(dateString: String): Flow<List<WaterLog>> {
        return waterDao.getLogsForDate(dateString).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTodayTotalIntake(dateString: String): Flow<Int> {
        return waterDao.getTotalIntakeForDate(dateString)
    }

    override suspend fun addWaterIntake(amountMl: Int, containerType: String): Long {
        val entity = WaterIntakeEntity(
            amountMl = amountMl,
            timestamp = System.currentTimeMillis(),
            dateString = LocalDate.now().toString(),
            containerType = containerType
        )
        return waterDao.insertLog(entity)
    }

    override suspend fun deleteWaterLog(id: Long) {
        waterDao.deleteById(id)
    }

    override suspend fun undoLastLog(): WaterLog? {
        val last = waterDao.getLastLog() ?: return null
        waterDao.deleteLog(last)
        return last.toDomain()
    }

    override fun getUserProfile(): Flow<UserProfile> {
        return preferencesRepository.userProfileFlow
    }

    override suspend fun updateUserProfile(profile: UserProfile) {
        preferencesRepository.updateProfile(profile)
    }

    override fun getHistorySummaries(limitDays: Int): Flow<Map<String, Int>> {
        return waterDao.getDailySummaries(limitDays).map { list ->
            list.associate { it.dateString to it.totalMl }
        }
    }

    private fun WaterIntakeEntity.toDomain() = WaterLog(
        id = id,
        amountMl = amountMl,
        timestamp = timestamp,
        dateString = dateString,
        containerType = containerType
    )
}
