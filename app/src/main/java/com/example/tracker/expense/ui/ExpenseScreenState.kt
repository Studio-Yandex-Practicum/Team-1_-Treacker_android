package com.example.tracker.expense.ui

import com.example.tracker.expense.domain.models.Category

sealed class ExpenseScreenState {
    data class AddExpenseState(val categories: List<Category>) : ExpenseScreenState()
    data object CreateCategory : ExpenseScreenState()
}