package com.example.tracker.expense.ui

import androidx.lifecycle.ViewModel
import com.example.tracker.expense.domain.GetAllCategoriesUseCase

class ExpenseViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

}