package com.example.tracker.settings.data.api

import android.content.SharedPreferences
import androidx.core.content.edit

class SettingsStorage(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val SETTINGS = "settings"
    }

    fun updateSettings(settingsJson: String) {
        sharedPreferences.edit { putString(SETTINGS, settingsJson) }
    }

    fun getSavedSettings(): String? {
        return sharedPreferences.getString(SETTINGS, "")
    }

}