package com.example.tracker.viewingAnalyticsCategories.domain


import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesUseCase {
    suspend fun execute(): Flow<List<Category>>
}