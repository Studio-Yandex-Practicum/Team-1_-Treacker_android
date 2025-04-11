package com.example.tracker.settings.domain.model

data class Settings(
    val darkTheme: Boolean = false,
    val notifications: Notifications = Notifications(),
    val currency: Currency = Currency.RUB
) {
    companion object {
        const val DARK_THEME = "dark_theme"
    }
}

data class Notifications(
    val active: Boolean = false,
    val time: String = "12:00",
    val message: String = "Не забудьте внести расходы"
)

sealed interface Currency {
    data object RUB : Currency
    data object USD : Currency
    data object EUR : Currency
}