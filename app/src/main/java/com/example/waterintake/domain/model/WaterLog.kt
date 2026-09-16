package com.example.waterintake.domain.model

data class WaterLog(
    val id: Long,
    val amountMl: Int,
    val timestamp: Long,
    val dateString: String,
    val containerType: String
)

data class UserProfile(
    val name: String,
    val weightKg: Int,
    val age: Int,
    val activityLevel: String, // sedentary, light, moderate, high
    val dailyGoalMl: Int,
    val useCustomGoal: Boolean,
    val customGoalMl: Int,
    val notificationsEnabled: Boolean,
    val reminderIntervalHours: Int,
    val wakeTime: String,
    val sleepTime: String,
    val hasCompletedOnboarding: Boolean
) {
    val activeGoalMl: Int
        get() = if (useCustomGoal) customGoalMl else dailyGoalMl
}
