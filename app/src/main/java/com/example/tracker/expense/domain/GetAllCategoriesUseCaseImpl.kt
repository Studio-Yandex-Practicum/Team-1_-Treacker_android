package com.example.tracker.expense.domain

import android.util.Log
import com.example.tracker.core.domain.ExpensesRepository
import com.example.tracker.expense.domain.models.Category
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCaseImpl(
    private val repository: ExpensesRepository
) : GetAllCategoriesUseCase {

    override suspend fun execute(): Flow<List<Category>> {
        return repository.getAllCategory()
    }
}