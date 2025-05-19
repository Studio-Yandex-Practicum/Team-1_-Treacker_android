package com.example.tracker.core.db.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracker.core.db.dao.CategoryDao

@Entity(tableName = CategoryDao.TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @DrawableRes
    val icon: Int,
    @ColumnInfo("icon_color")
    @ColorRes
    val iconColor: Int,
    @ColumnInfo("icon_tint")
    @ColorRes
    val iconTint: Int
)