package com.example.tracker.expense.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.expense.domain.GetAllCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                        ExpenseScreenState.AddExpenseState(categories)
                    )
                }
        }
    }
}