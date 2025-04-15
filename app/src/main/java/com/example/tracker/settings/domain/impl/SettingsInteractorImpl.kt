package com.example.tracker.settings.domain.impl

import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.domain.api.SettingsRepository
import com.example.tracker.settings.domain.model.Currency
import com.example.tracker.settings.domain.model.Notifications
import com.example.tracker.settings.domain.model.Settings

class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository
) : SettingsInteractor {

    override fun getSavedSettings(): Settings {
        return settingsRepository.getSavedSettings()
    }
    override fun updateSettings(settings: Settings) {
        settingsRepository.updateSettings(settings)
    }

    override fun getThemeSettings(): Boolean {
        val settings = getSavedSettings()
        return settings.darkTheme
    }
    override fun setThemeSettings(darkTheme: Boolean) {
        val settings = getSavedSettings()
        updateSettings(settings.copy(darkTheme = darkTheme))
    }

    override fun getCurrency(): Currency {
        val settings = getSavedSettings()
        return settings.currency
    }
    override fun setCurrency(currency: Currency) {
        val settings = getSavedSettings()
        updateSettings(settings.copy(currency = currency))
    }

    override fun getNotificationSettings(): Notifications {
        val settings = getSavedSettings()
        return settings.notifications
    }
    override fun setNotificationSettings(notifications: Notifications) {
        val settings = getSavedSettings()
        updateSettings(settings.copy(notifications = notifications))
    }
    override fun setNotificationToggle(active: Boolean) {
        val notificationSettings = getNotificationSettings()
        setNotificationSettings(notificationSettings.copy(active = active))
    }
    override fun setNotificationTime(time: String) {
        val notificationSettings = getNotificationSettings()
        setNotificationSettings(notificationSettings.copy(time = time))
    }
    override fun setNotificationMessage(message: String) {
        val notificationSettings = getNotificationSettings()
        setNotificationSettings(notificationSettings.copy(message = message))
    }
}