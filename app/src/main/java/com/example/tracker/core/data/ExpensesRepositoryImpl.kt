package com.example.tracker.core.data

import com.example.tracker.core.data.converters.CategoryConverter
import com.example.tracker.core.db.AppDatabase
import com.example.tracker.core.db.entity.CategoryEntity
import com.example.tracker.core.domain.ExpensesRepository
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpensesRepositoryImpl(
    private val db: AppDatabase,
    private val converter: CategoryConverter
) : ExpensesRepository {

    override suspend fun getAllCategory(): Flow<List<Category>> = flow {
        emit(
            convertToCategories(db.categoryDao().getAllCategories())
        )
    }

    override suspend fun checkCategoryEmpty(): Boolean {
        return db.categoryDao().getAllCategories().isEmpty()
    }

    override suspend fun saveCategory(category: Category) {
        db.categoryDao().saveCategory(converter.map(category))
    }

    override suspend fun addCategories(categories: List<Category>) {
        db.categoryDao().addCategories(convertToCategoryEntities(categories))
    }

    private fun convertToCategoryEntities(categories: List<Category>): List<CategoryEntity> {
        return categories.map { converter.map(it) }
    }

    private fun convertToCategories(categoryEntities: List<CategoryEntity>): List<Category> {
        return categoryEntities.map { converter.map(it) }
    }

}