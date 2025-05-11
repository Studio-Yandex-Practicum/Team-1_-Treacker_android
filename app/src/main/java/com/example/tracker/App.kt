package com.example.tracker

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.tracker.di.dataModule
import com.example.tracker.di.interactorModule
import com.example.tracker.di.repositoryModule
import com.example.tracker.di.viewModelModule
import com.example.tracker.settings.data.api.SettingsStorage
import com.example.tracker.settings.data.impl.SettingsRepositoryImpl
import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.domain.api.SettingsRepository
import com.example.tracker.settings.domain.impl.SettingsInteractorImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private var darkTheme = false
    private lateinit var settingsInteractor: SettingsInteractor

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule,repositoryModule, viewModelModule)
        }

        settingsInteractor = SettingsInteractorImpl(getSettingsRepository(this))
        darkTheme = settingsInteractor.getThemeSettings()

        if (darkTheme) {
            switchTheme(darkTheme)
        } else {
            switchTheme(darkTheme)
        }
    }

    fun switchTheme(darkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private fun getSettingsRepository(context: Context): SettingsRepository {
        val settingsPrefs = context.getSharedPreferences("SettingsStorage", Context.MODE_PRIVATE)
        val localPrefs = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        val settingsStorage = SettingsStorage(settingsPrefs, localPrefs)
        val gson = Gson()
        return SettingsRepositoryImpl(
            settingsStorage,
            gson,
            settingsPrefs
        )
    }
}
