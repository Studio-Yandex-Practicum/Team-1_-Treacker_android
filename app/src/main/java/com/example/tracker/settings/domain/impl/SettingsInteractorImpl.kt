package com.example.tracker.settings.domain.impl

import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.domain.api.SettingsRepository
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
}