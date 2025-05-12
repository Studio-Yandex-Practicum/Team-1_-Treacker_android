package com.example.tracker.util

import com.example.tracker.viewingAnalyticsCategories.domain.models.Category

sealed interface CategoryState {
    data class Empty(val message: String) : CategoryState
    data class Error(val message: String) : CategoryState
    data class Content(val data: List<Category>) : CategoryState
}