package com.example.tracker.core.data.converters

import com.example.tracker.expense.domain.models.Category
import com.example.tracker.core.db.entity.CategoryEntity

class CategoryConverter {
    fun map(category: Category): CategoryEntity {
        return CategoryEntity(
            title = category.title,
            icon = category.icon,
            iconColor = category.iconColor,
            iconTint = category.iconTint,
        )
    }

    fun map(categoryEntity: CategoryEntity): Category {
        return Category(
            id = categoryEntity.id,
            title = categoryEntity.title,
            icon = categoryEntity.icon,
            iconColor = categoryEntity.iconColor,
            iconTint = categoryEntity.iconTint
        )
    }
}