package com.example.tracker.settings.domain.model

data class Settings(
    val darkTheme: Boolean = false,
    val notifications: Notifications = Notifications(),
    val currency: Currency = Currency.RUB
)

data class Notifications(
    val active: Boolean = false,
    val time: String = "12:00",
    val message: String = "Не забудьте внести расходы"
)

enum class Currency {
    USD, EUR, RUB
}