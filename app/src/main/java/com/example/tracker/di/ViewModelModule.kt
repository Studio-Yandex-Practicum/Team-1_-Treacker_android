package com.example.tracker.di

import com.example.tracker.registration.ui.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{RegistrationViewModel(get())}
}