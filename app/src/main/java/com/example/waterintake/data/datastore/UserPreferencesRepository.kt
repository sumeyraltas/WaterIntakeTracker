package com.example.waterintake.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.waterintake.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.userDataStore

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val WEIGHT_KG = intPreferencesKey("weight_kg")
        val AGE = intPreferencesKey("age")
        val ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
        val DAILY_GOAL_ML = intPreferencesKey("daily_goal_ml")
        val USE_CUSTOM_GOAL = booleanPreferencesKey("use_custom_goal")
        val CUSTOM_GOAL_ML = intPreferencesKey("custom_goal_ml")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val REMINDER_INTERVAL_HOURS = intPreferencesKey("reminder_interval_hours")
        val WAKE_TIME = stringPreferencesKey("wake_time")
        val SLEEP_TIME = stringPreferencesKey("sleep_time")
        val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
    }

    val userProfileFlow: Flow<UserProfile> = dataStore.data.map { pref ->
        UserProfile(
            name = pref[PreferencesKeys.USER_NAME] ?: "Kullanıcı",
            weightKg = pref[PreferencesKeys.WEIGHT_KG] ?: 70,
            age = pref[PreferencesKeys.AGE] ?: 28,
            activityLevel = pref[PreferencesKeys.ACTIVITY_LEVEL] ?: "moderate",
            dailyGoalMl = pref[PreferencesKeys.DAILY_GOAL_ML] ?: 2800,
            useCustomGoal = pref[PreferencesKeys.USE_CUSTOM_GOAL] ?: false,
            customGoalMl = pref[PreferencesKeys.CUSTOM_GOAL_ML] ?: 2800,
            notificationsEnabled = pref[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true,
            reminderIntervalHours = pref[PreferencesKeys.REMINDER_INTERVAL_HOURS] ?: 2,
            wakeTime = pref[PreferencesKeys.WAKE_TIME] ?: "08:30",
            sleepTime = pref[PreferencesKeys.SLEEP_TIME] ?: "23:00",
            hasCompletedOnboarding = pref[PreferencesKeys.HAS_COMPLETED_ONBOARDING] ?: false
        )
    }

    suspend fun updateProfile(profile: UserProfile) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.USER_NAME] = profile.name
            pref[PreferencesKeys.WEIGHT_KG] = profile.weightKg
            pref[PreferencesKeys.AGE] = profile.age
            pref[PreferencesKeys.ACTIVITY_LEVEL] = profile.activityLevel
            pref[PreferencesKeys.DAILY_GOAL_ML] = profile.dailyGoalMl
            pref[PreferencesKeys.USE_CUSTOM_GOAL] = profile.useCustomGoal
            pref[PreferencesKeys.CUSTOM_GOAL_ML] = profile.customGoalMl
            pref[PreferencesKeys.NOTIFICATIONS_ENABLED] = profile.notificationsEnabled
            pref[PreferencesKeys.REMINDER_INTERVAL_HOURS] = profile.reminderIntervalHours
            pref[PreferencesKeys.WAKE_TIME] = profile.wakeTime
            pref[PreferencesKeys.SLEEP_TIME] = profile.sleepTime
            pref[PreferencesKeys.HAS_COMPLETED_ONBOARDING] = profile.hasCompletedOnboarding
        }
    }

    suspend fun completeOnboarding() {
        dataStore.edit { pref ->
            pref[PreferencesKeys.HAS_COMPLETED_ONBOARDING] = true
        }
    }
}
