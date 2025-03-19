package com.example.langapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.langapp.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Flow<CategoryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :name || '%'")
    fun getCategoriesByName(name: String): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE transl LIKE '%' || :transl || '%'")
    fun getCategoriesByTransl(transl: String): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE transcr LIKE '%' || :transcr || '%'")
    fun getCategoriesByTranscr(transcr: String): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY name")
    fun getCategoriesSortedByName(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY transl")
    fun getCategoriesSortedByTransl(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY transcr")
    fun getCategoriesSortedByTranscr(): Flow<List<CategoryEntity>>
}