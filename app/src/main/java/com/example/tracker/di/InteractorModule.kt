package com.example.tracker.di

import com.example.tracker.authorization.data.AuthorizationInteractor
import com.example.tracker.authorization.domain.interactor.AuthorizationInteractorImpl
import com.example.tracker.registration.data.RegistrationInteractor
import com.example.tracker.registration.domain.interactor.RegistrationInteractorImpl
import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.domain.impl.SettingsInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<RegistrationInteractor> {
        RegistrationInteractorImpl(get())
    }
    single<AuthorizationInteractor> {
        AuthorizationInteractorImpl(get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }
}