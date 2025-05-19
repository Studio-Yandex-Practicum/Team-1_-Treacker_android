package com.example.tracker.settings.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tracker.settings.domain.api.SettingsInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Менеджер, который отслеживает перезагрузку телефона
 */
class BootReceiver : BroadcastReceiver(), KoinComponent {
    private val settingsInteractor: SettingsInteractor by inject()

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val settings = settingsInteractor.getNotificationSettings()
            if (settings.active) {
                NotificationScheduler.scheduleDailyReminder(context)
            }
        }
    }
}