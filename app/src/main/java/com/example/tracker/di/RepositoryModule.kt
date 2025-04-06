package com.example.tracker.di

import com.example.tracker.authorization.data.repository.AuthorizationRepositoryImpl
import com.example.tracker.authorization.domain.repository.AuthorizationRepository
import com.example.tracker.registration.data.repository.RegistrationRepositoryImpl
import com.example.tracker.registration.domain.repository.RegistrationRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RegistrationRepository> {
        RegistrationRepositoryImpl(get())
    }
    single<AuthorizationRepository> {
        AuthorizationRepositoryImpl(get())
    }
}