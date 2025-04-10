package com.example.tracker.settings.domain.model

data class Settings(
    var darkTheme: Boolean = false,
    var notifications: Notifications = Notifications(),
    var currency: Currency = Currency.RUB
) {
    companion object {
        const val DARK_THEME = "dark_theme"
    }
}

data class Notifications(
    var active: Boolean = false,
    var time: String = "12:00",
    var message: String = "Не забудьте внести расходы"
)

sealed interface Currency {
    data object RUB : Currency
    data object USD : Currency
    data object EUR : Currency
}