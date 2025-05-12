package com.example.tracker.di

import com.example.tracker.authorization.data.repository.AuthorizationRepositoryImpl
import com.example.tracker.authorization.domain.repository.AuthorizationRepository
import com.example.tracker.registration.data.repository.RegistrationRepositoryImpl
import com.example.tracker.registration.domain.repository.RegistrationRepository
import com.example.tracker.core.data.ExpensesRepositoryImpl
import com.example.tracker.core.data.converters.CategoryConverter
import com.example.tracker.core.domain.ExpensesRepository
import com.example.tracker.settings.data.impl.SettingsRepositoryImpl
import com.example.tracker.settings.domain.api.SettingsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RegistrationRepository> {
        RegistrationRepositoryImpl(get(), get())
    }
    single<AuthorizationRepository> {
        AuthorizationRepositoryImpl(get(), get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get(), get())
    }
    single<ExpensesRepository> {
        ExpensesRepositoryImpl(get(), get())
    }
    factory { CategoryConverter() }
}