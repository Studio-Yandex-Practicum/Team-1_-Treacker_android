package com.example.tracker.di

import com.example.tracker.authorization.data.AuthorizationInteractor
import com.example.tracker.authorization.domain.interactor.AuthorizationInteractorImpl
import com.example.tracker.registration.data.RegistrationInteractor
import com.example.tracker.registration.domain.interactor.RegistrationInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<RegistrationInteractor> {
        RegistrationInteractorImpl(get())
    }
    single<AuthorizationInteractor> {
        AuthorizationInteractorImpl(get())
    }
}