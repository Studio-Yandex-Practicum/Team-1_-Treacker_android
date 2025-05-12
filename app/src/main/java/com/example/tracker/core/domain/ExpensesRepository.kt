package com.example.tracker.core.domain

import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    suspend fun getAllCategory(): Flow<List<Category>>
    suspend fun checkCategoryEmpty(): Boolean
    suspend fun saveCategory(category: Category)
    suspend fun addCategories(categories: List<Category>)
}