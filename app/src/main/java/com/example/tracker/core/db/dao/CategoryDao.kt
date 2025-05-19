package com.example.tracker.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tracker.core.db.entity.CategoryEntity

@Dao
interface CategoryDao {

    companion object {
        const val TABLE_NAME = "Category"
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategories(categoryEntity: List<CategoryEntity>)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllCategories(): List<CategoryEntity>
}