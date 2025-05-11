package com.example.tracker.settings.data.impl

import android.content.SharedPreferences
import com.example.tracker.settings.data.api.SettingsStorage
import com.example.tracker.settings.domain.api.SettingsRepository
import com.example.tracker.settings.domain.model.Settings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingsRepositoryImpl(
    private val settingsStorage: SettingsStorage,
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    val editor = sharedPreferences.edit()

    override fun getSavedSettings(): Settings {
        val type = object : TypeToken<Settings>() {}.type
        val settingsJson = settingsStorage.getSavedSettings()
        return gson.fromJson(settingsJson, type) ?: Settings()
    }

    override fun updateSettings(settings: Settings) {
        settingsStorage.updateSettings(gson.toJson(settings))
    }
    override suspend fun setAccessToken(accessToken: String) {
        editor.putString("access_token", accessToken)
        editor.apply()
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }

    override suspend fun setIdToken(idToken: Int) {
        editor.putString("user_id", idToken.toString())
        editor.apply()
    }
}