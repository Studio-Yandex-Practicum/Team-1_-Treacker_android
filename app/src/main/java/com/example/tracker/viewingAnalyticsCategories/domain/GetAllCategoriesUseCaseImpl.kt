package com.example.tracker.viewingAnalyticsCategories.domain

import com.example.tracker.core.domain.ExpensesRepository
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category

import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCaseImpl(
    private val repository: ExpensesRepository
) : GetAllCategoriesUseCase {

    override suspend fun execute(): Flow<List<Category>> {
        return repository.getAllCategory()
    }
}