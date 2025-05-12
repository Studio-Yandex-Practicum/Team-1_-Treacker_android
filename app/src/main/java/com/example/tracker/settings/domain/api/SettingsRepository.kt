package com.example.tracker.settings.domain.api

import com.example.tracker.settings.domain.model.Settings

interface SettingsRepository {
    fun getSavedSettings(): Settings
    fun updateSettings(settings: Settings)
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun setIdToken(idToken: Int)
}