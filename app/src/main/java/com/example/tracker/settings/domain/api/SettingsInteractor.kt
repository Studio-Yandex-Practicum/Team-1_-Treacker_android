package com.example.tracker.settings.domain.api

import com.example.tracker.settings.domain.model.Currency
import com.example.tracker.settings.domain.model.Notifications
import com.example.tracker.settings.domain.model.Settings

interface SettingsInteractor {
    fun getSavedSettings(): Settings
    fun updateSettings(settings: Settings)

    fun getThemeSettings(): Boolean
    fun setThemeSettings(darkTheme: Boolean)

    fun getCurrency(): Currency
    fun setCurrency(currency: Currency)

    fun getNotificationSettings(): Notifications
    fun setNotificationSettings(notifications: Notifications)
    fun setNotificationToggle(active: Boolean)
    fun setNotificationTime(time: String)
    fun setNotificationMessage(message: String)
}