package com.example.tracker.util

import com.example.tracker.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toAppDateFormat(): String {
    return if (this.day == Date().day) {
        R.string.today.toString()
    } else {
        SimpleDateFormat("dd MMMM", Locale.forLanguageTag("ru"))
            .format(this)
    }
}