package com.example.tracker.expense.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.R
import com.example.tracker.expense.domain.GetAllCategoriesUseCase
import com.example.tracker.expense.domain.models.Category
import com.example.tracker.util.toAppDateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ExpenseScreenState>()
    val state: LiveData<ExpenseScreenState> = _state

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCategoriesUseCase
                .execute()
                .collect { categories ->
                    _state.postValue(
                        ExpenseScreenState.AddExpenseState(
                            categories = categories + createAddItem(),
                            date = Date().toAppDateFormat()
                        )
                    )
                }
        }
    }

    private fun createAddItem(): List<Category> {
        return listOf(
            Category(
                title = "Добавить",
                icon = R.drawable.category_icon_add,
                iconColor = R.color.ic_gray_bg,
                iconTint = R.color.ic_gray_primary
            )
        )
    }
}