package com.example.tracker.settings.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.ui.notifications.model.NotificationFragmentState

class NotificationsViewModel(private val settingsInteractor: SettingsInteractor) : ViewModel() {

    private val stateLiveData = MutableLiveData<NotificationFragmentState>()
    fun observeState(): LiveData<NotificationFragmentState> = mediatorState

    private val mediatorState = MediatorLiveData<NotificationFragmentState>().also { liveData ->
        liveData.addSource(stateLiveData) { notificationFragmentState ->
            liveData.value = when (notificationFragmentState) {
                is NotificationFragmentState.ShowNumberPicker -> notificationFragmentState
                is NotificationFragmentState.HideNumberPicker -> notificationFragmentState
                is NotificationFragmentState.Binding ->
                    NotificationFragmentState.Binding(
                        notificationFragmentState.notificationSettings
                    )
            }
        }
    }

    init {
        val notificationSettings = settingsInteractor.getNotificationSettings()
        renderState(NotificationFragmentState.Binding(notificationSettings))
    }

    private fun renderState(state: NotificationFragmentState) {
        stateLiveData.postValue(state)
    }

    fun toggleNotifications(isEnabled: Boolean) {
        settingsInteractor.setNotificationToggle(isEnabled)
    }

    fun onTimeCardClick() {
        val newState = when (stateLiveData.value) {
            is NotificationFragmentState.ShowNumberPicker ->
                NotificationFragmentState.HideNumberPicker
            else ->
                NotificationFragmentState.ShowNumberPicker
        }
        renderState(newState)
    }

    fun setHours(hours: String) {
        val settings = settingsInteractor.getNotificationSettings()
        val (savedHours, savedMinutes) = settings.time.split(":").map { it }
        settingsInteractor.setNotificationTime("$hours:$savedMinutes")
    }
    fun setMinutes(minutes: String) {
        val settings = settingsInteractor.getNotificationSettings()
        val (savedHours, savedMinutes) = settings.time.split(":").map { it }
        settingsInteractor.setNotificationTime("$savedHours:$minutes")
    }

    fun setMessage(message: String) {
        settingsInteractor.setNotificationMessage(message)
    }
}