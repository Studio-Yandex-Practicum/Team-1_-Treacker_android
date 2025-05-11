package com.example.tracker.settings.data.api

import android.content.SharedPreferences
import androidx.core.content.edit

class SettingsStorage(
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesLocal: SharedPreferences) {

    private companion object {
        const val SETTINGS = "settings"
        const val LOCAL = "LocalStorage"
    }

    fun updateSettings(settingsJson: String) {
        sharedPreferences.edit { putString(SETTINGS, settingsJson) }
    }

    fun getSavedSettings(): String? {
        return sharedPreferences.getString(SETTINGS, "")
    }
    fun updateSettingsLocal(settingsJson: String) {
        sharedPreferencesLocal.edit {
            putString(LOCAL, settingsJson)
            apply()
        }
    }

    fun getSavedSettingsLocal(): String? {
        return sharedPreferencesLocal.getString(LOCAL, null)
    }

}