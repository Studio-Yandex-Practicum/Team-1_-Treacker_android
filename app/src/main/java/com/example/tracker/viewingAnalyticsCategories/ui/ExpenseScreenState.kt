package com.example.tracker.viewingAnalyticsCategories.ui

import com.example.tracker.viewingAnalyticsCategories.domain.models.Category

sealed class ExpenseScreenState {
    data class AddExpenseState(
        val categories:List<Category>,
        val date: String
    ) : ExpenseScreenState()
    data object CreateCategory : ExpenseScreenState()
}