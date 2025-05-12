package com.example.tracker.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tracker.core.db.dao.OperationDao

@Entity(
    tableName = OperationDao.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("category_id")
    val categoryId: Int,
    val sum: Long,
    val timestamp: Long,
    val comment: String
)
