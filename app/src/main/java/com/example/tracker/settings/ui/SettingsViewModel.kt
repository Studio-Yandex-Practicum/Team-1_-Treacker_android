package com.example.tracker.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracker.settings.domain.api.SettingsInteractor

class SettingsViewModel(private val settingsInteractor: SettingsInteractor) : ViewModel() {

    private val _themeSettings = MutableLiveData<Boolean>()
    fun getThemeSettingsLiveData(): LiveData<Boolean> = _themeSettings

    fun getThemeSettings(): Boolean {
        return settingsInteractor.getThemeSettings()
    }
    fun switchTheme(darkTheme: Boolean) {
        settingsInteractor.setThemeSettings(darkTheme)
        _themeSettings.value = settingsInteractor.getThemeSettings()
    }

}