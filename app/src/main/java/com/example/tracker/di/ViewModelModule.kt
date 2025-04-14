package com.example.tracker.di

import com.example.tracker.authorization.ui.AuthorizationViewModel
import com.example.tracker.registration.ui.RegistrationViewModel
import com.example.tracker.settings.ui.currency.CurrencyViewModel
import com.example.tracker.settings.ui.main.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegistrationViewModel(get()) }
    viewModel { AuthorizationViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { CurrencyViewModel(get()) }
}