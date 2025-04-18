package com.example.tracker.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracker.core.db.dao.CategoryDao
import com.example.tracker.core.db.dao.OperationDao
import com.example.tracker.core.db.entity.CategoryEntity
import com.example.tracker.core.db.entity.OperationEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        OperationEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun operationDao(): OperationDao
}