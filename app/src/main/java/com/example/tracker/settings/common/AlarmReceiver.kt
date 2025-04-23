package com.example.tracker.settings.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tracker.settings.domain.api.SettingsInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Будильник, который выводит сообщение и перезапускает напоминание на следующий день
 */
class AlarmReceiver : BroadcastReceiver(), KoinComponent {
    private val settingsInteractor: SettingsInteractor by inject()

    override fun onReceive(context: Context, intent: Intent?) {
        val settings = settingsInteractor.getNotificationSettings()
        if (!settings.active) return

        NotificationHelper.showNotification(context, settings.message)
        NotificationScheduler.scheduleDailyReminder(context)
    }
}