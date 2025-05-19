package com.example.tracker.util

import com.example.tracker.registration.domain.model.Registration

sealed interface RegistrationState {
    data class Empty(val message: String) : RegistrationState
    data class Error(val message: String) : RegistrationState
    data class Content(val data: Registration) : RegistrationState
}