package com.example.tracker.viewingAnalyticsCategories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.R
import com.example.tracker.util.CategoryState
import com.example.tracker.util.toAppDateFormat
import com.example.tracker.viewingAnalyticsCategories.domain.GetAllCategoriesUseCase
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AnalyticsViewModel(private val getAllCategoriesUseCase: GetAllCategoriesUseCase) :
    ViewModel() {
    init {
        getCategories()
    }

    private val categoryState = MutableLiveData<CategoryState>()
    fun getCategoryState(): LiveData<CategoryState> = categoryState

    private val _state = MutableLiveData<ExpenseScreenState>()
    val state: LiveData<ExpenseScreenState> = _state

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