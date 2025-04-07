package com.example.langapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.langapp.data.entities.CategoryTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryTime(categoryTime: CategoryTimeEntity)

    @Update
    suspend fun updateCategoryTime(categoryTime: CategoryTimeEntity)

    @Query("SELECT * FROM category_time WHERE categoryId = :categoryId")
    fun getCategoryTimeById(categoryId: Int): Flow<CategoryTimeEntity?>

    @Query("SELECT * FROM category_time")
    fun getAllCategoryTime(): Flow<List<CategoryTimeEntity>>
}


