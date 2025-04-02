package com.example.tracker.di

import com.example.tracker.registration.data.repository.RegistrationRepositoryImpl
import com.example.tracker.registration.domain.repository.RegistrationRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RegistrationRepository> {
        RegistrationRepositoryImpl(get())
    }
}