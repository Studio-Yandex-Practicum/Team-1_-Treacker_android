package com.example.tracker.viewingAnalyticsCategories.domain.models

import java.util.Date

data class Operation(
    val id: Int,
    val sum: Float,
    val categoryId: Int,
    val date: Date,
    val comment: String
)
