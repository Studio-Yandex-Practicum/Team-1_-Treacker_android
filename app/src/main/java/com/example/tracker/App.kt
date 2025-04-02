package com.example.tracker

import android.app.Application
import com.example.tracker.di.dataModule
import com.example.tracker.di.interactorModule
import com.example.tracker.di.repositoryModule
import com.example.tracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule,repositoryModule, viewModelModule)
        }
    }
}
