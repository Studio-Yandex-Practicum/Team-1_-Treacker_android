package com.example.tracker.addExpense.domain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Category(
    val id: Int,
    val title: String,
    @DrawableRes
    val icon: Int,
    @ColorRes
    val iconColor: Int,
    @ColorRes
    val iconTint: Int,
    val operationCount: Int,
    val operationSum: Float,
    val percentageShare: Int,
)