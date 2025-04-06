package com.example.tracker.util

import com.example.tracker.authorization.domain.model.Login

sealed interface LoginState {
    data class Empty(val message: String) : LoginState
    data class Error(val message: String) : LoginState
    data class Content(val data: Login) : LoginState
}