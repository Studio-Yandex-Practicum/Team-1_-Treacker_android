package com.example.tracker.expense.domain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Category(
    val id: Int = 0,
    val title: String,
    @DrawableRes
    val icon: Int,
    @ColorRes
    val iconColor: Int,
    @ColorRes
    val iconTint: Int,
    val operationCount: Int = 0,
    val operationSum: Float = 0f,
    val percentageShare: Int = 0,
)