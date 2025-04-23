package com.example.tracker.settings.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.tracker.R

/**
 * Создает канал для уведомлений и само уведомление
 */
object NotificationHelper {
    private const val CHANNEL_ID = "daily_notification_channel"
    private const val CHANNEL_NAME = "Daily Reminder"

    /**
     * Показывает уведомление с переданным сообщением и делает deep link в SettingsFragment
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel for daily reminders"
                }
                manager.createNotificationChannel(channel)
            }
        }
    }

    /**
     * Показывает уведомление с переданным сообщением и делает deep link в SettingsFragment
     */
    fun showNotification(context: Context, message: String) {
        createNotificationChannel(context)

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.settingsFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Напоминание")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1001, notification)
    }
}