package com.example.tracker.expense.domain

import com.example.tracker.expense.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesUseCase {
    suspend fun execute(): Flow<List<Category>>
}