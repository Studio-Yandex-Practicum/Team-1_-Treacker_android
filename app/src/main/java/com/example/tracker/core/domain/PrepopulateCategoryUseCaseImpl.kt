package com.example.tracker.core.domain

import com.example.tracker.util.AssetDatabase

class PrepopulateCategoryUseCaseImpl(
    private val repository: ExpensesRepository
) : PrepopulateCategoryUseCase {

    override suspend fun execute() {
        if (repository.checkCategoryEmpty()) {
            repository.addCategories(AssetDatabase.prepopulateCategories)
        }
    }
}