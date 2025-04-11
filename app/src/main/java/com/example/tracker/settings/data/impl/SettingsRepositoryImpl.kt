package com.example.tracker.settings.data.impl

import com.example.tracker.settings.data.api.SettingsStorage
import com.example.tracker.settings.domain.api.SettingsRepository
import com.example.tracker.settings.domain.model.Settings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingsRepositoryImpl(
    private val settingsStorage: SettingsStorage,
    private val gson: Gson
) : SettingsRepository {

    override fun getSavedSettings(): Settings {
        val type = object : TypeToken<Settings>() {}.type
        val settingsJson = settingsStorage.getSavedSettings()
        return gson.fromJson(settingsJson, type) ?: Settings()
    }

    override fun updateSettings(settings: Settings) {
        settingsStorage.updateSettings(gson.toJson(settings))
    }
}