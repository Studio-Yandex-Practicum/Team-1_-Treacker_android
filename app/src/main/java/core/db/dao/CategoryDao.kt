package core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import core.db.entity.CategoryEntity

@Dao
interface CategoryDao {

    companion object {
        const val TABLE_NAME = "Category"
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllCategories(): List<CategoryEntity>
}