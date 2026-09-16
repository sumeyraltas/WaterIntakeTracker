package com.example.waterintake.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.waterintake.MainActivity
import com.example.waterintake.R
import com.example.waterintake.domain.repository.WaterRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalTime

@HiltWorker
class WaterReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: WaterRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val profile = repository.getUserProfile().first()
        if (!profile.notificationsEnabled) return Result.success()

        val now = LocalTime.now()
        val wake = LocalTime.parse(profile.wakeTime)
        val sleep = LocalTime.parse(profile.sleepTime)

        // Uyanık saatler kontrolü
        if (now.isBefore(wake) || now.isAfter(sleep)) {
            return Result.success()
        }

        val todayTotal = repository.getTodayTotalIntake(LocalDate.now().toString()).first()
        val activeGoal = profile.activeGoalMl

        // Zaten hedef tamamlandıysa rahatsız etme
        if (todayTotal >= activeGoal) {
            return Result.success()
        }

        val remaining = activeGoal - todayTotal
        sendNotification(remaining)
        return Result.success()
    }

    private fun sendNotification(remainingMl: Int) {
        val channelId = "water_intake_reminders"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Su İçme Hatırlatıcıları",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Günün su hedefine ulaşmanız için periyodik hatırlatmalar."
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("Su Vakti! 💧")
            .setContentText("Bugünkü hedefinize ulaşmak için ${remainingMl}ml kaldı. Bir bardak su için!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(1001, notification)
    }
}
