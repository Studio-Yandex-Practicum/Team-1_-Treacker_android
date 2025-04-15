package com.example.tracker.settings.utils

import java.util.Locale

object TimeFormatterUtil {
    fun formatTwoDigits(value: Int): String {
        return String.format(Locale.getDefault(), "%02d", value)
    }
}