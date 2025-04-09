package core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import core.db.dao.CategoryDao
import core.db.dao.OperationDao
import core.db.entity.CategoryEntity
import core.db.entity.OperationEntity

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