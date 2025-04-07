package com.example.langapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_time")
data class CategoryTimeEntity(
    @PrimaryKey val categoryId: Int,
    @ColumnInfo(name = "time_spent_millis") var timeSpentMillis: Long = 0L
)