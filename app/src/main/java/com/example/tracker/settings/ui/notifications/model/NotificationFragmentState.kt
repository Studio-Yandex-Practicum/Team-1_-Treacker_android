package com.example.tracker.settings.ui.notifications.model

import com.example.tracker.settings.domain.model.Notifications

sealed interface NotificationFragmentState {
    data object ShowNumberPicker : NotificationFragmentState
    data object HideNumberPicker : NotificationFragmentState

    data class Binding(
        val notificationSettings: Notifications
    ) : NotificationFragmentState
}