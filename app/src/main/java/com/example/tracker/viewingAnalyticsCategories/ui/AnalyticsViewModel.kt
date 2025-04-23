package com.example.tracker.viewingAnalyticsCategories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.expense.domain.GetAllCategoriesUseCase
import com.example.tracker.expense.domain.models.Category
import com.example.tracker.util.CategoryState
import kotlinx.coroutines.launch

class AnalyticsViewModel(private val getAllCategoriesUseCase: GetAllCategoriesUseCase) :
    ViewModel() {
    private val categoryState = MutableLiveData<CategoryState>()
    fun getCategoryState(): LiveData<CategoryState> = categoryState

    fun loadData() {
        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect { resource ->
                if (resource.isNullOrEmpty()) {
                    processError("В этом месяце нет расходов")
                } else {
                    processResult(resource)
                }
            }
        }
    }

    private fun processResult(data: List<Category>) {
        if (data == null) {
            categoryState.postValue(CategoryState.Empty("В этом месяце нет расходов"))
        } else {
            categoryState.postValue(CategoryState.Content(data))
        }
    }

    private fun processError(message: String) {
        categoryState.postValue(CategoryState.Error(message))
    }

}