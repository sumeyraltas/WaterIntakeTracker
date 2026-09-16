package com.example.waterintake.domain.usecase

import com.example.waterintake.domain.model.UserProfile
import com.example.waterintake.domain.model.WaterLog
import com.example.waterintake.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddWaterIntakeUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke(amountMl: Int, containerType: String = "glass"): Long {
        require(amountMl > 0) { "Su miktarı 0'dan büyük olmalıdır" }
        return repository.addWaterIntake(amountMl, containerType)
    }
}

class UndoWaterIntakeUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke(): WaterLog? = repository.undoLastLog()
}

class CalculateDailyGoalUseCase @Inject constructor() {
    /**
     * Endüstriyel sağlık formülü:
     * Kilo başına 35ml + Aktivite düzeyi farkı + Yaş dengeleyici
     */
    operator fun invoke(weightKg: Int, age: Int, activityLevel: String): Int {
        val baseMl = weightKg * 35
        val activityBonus = when (activityLevel.lowercase()) {
            "sedentary" -> 0
            "light" -> 350
            "moderate" -> 650
            "high" -> 1000
            else -> 500
        }
        val ageAdjustment = if (age > 55) -100 else if (age < 30) 100 else 0
        val total = baseMl + activityBonus + ageAdjustment

        // En yakın 50ml'ye yuvarla, minimum 1500ml, maksimum 5000ml
        return ((total + 25) / 50 * 50).coerceIn(1500, 5000)
    }
}
