package com.example.tracker.di

import com.example.tracker.registration.data.RegistrationInteractor
import com.example.tracker.registration.domain.interactor.RegistrationInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<RegistrationInteractor> {
        RegistrationInteractorImpl(get())
    }
}