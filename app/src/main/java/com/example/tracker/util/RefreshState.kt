package com.example.tracker.util

import com.example.tracker.authorization.domain.model.Refresh

sealed interface RefreshState {
    data class Empty(val message: String) : RefreshState
    data class Error(val message: String) : RefreshState
    data class Content(val data: Refresh) : RefreshState
}