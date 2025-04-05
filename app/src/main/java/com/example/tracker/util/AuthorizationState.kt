package com.example.tracker.util

import com.example.tracker.authorization.domain.model.Authorization

sealed interface AuthorizationState {
    data class Empty(val message: String) : AuthorizationState
    data class Error(val message: String) : AuthorizationState
    data class Content(val data: Authorization) : AuthorizationState
}