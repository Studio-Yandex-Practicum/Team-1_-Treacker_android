package com.example.tracker.settings.ui.currency

import androidx.lifecycle.ViewModel
import com.example.tracker.R
import com.example.tracker.settings.domain.api.SettingsInteractor
import com.example.tracker.settings.domain.model.Currency

class CurrencyViewModel(private val settingsInteractor: SettingsInteractor) : ViewModel() {

    fun getCheckedId(): Int {
        val currency = settingsInteractor.getCurrency()

        val id = when (currency) {
            Currency.RUB -> R.id.rubOpt
            Currency.USD -> R.id.usdOpt
            Currency.EUR -> R.id.eurOpt
        }
        return id
    }

    fun setCurrency(currency: Currency) {
        settingsInteractor.setCurrency(currency)
    }
}