package com.example.tracker.settings.domain.api

import com.example.tracker.settings.domain.model.Settings

interface SettingsInteractor {
    fun getSavedSettings(): Settings
    fun updateSettings(settings: Settings)
}