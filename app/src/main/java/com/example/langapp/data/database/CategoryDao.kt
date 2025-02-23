package com.example.langapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.langapp.data.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Flow<Category>

    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :name || '%'")
    fun getCategoriesByName(name: String): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE transl LIKE '%' || :transl || '%'")
    fun getCategoriesByTransl(transl: String): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE transcr LIKE '%' || :transcr || '%'")
    fun getCategoriesByTranscr(transcr: String): Flow<List<Category>>

    @Query("SELECT * FROM categories ORDER BY name")
    fun getCategoriesSortedByName(): Flow<List<Category>>

    @Query("SELECT * FROM categories ORDER BY transl")
    fun getCategoriesSortedByTransl(): Flow<List<Category>>

    @Query("SELECT * FROM categories ORDER BY transcr")
    fun getCategoriesSortedByTranscr(): Flow<List<Category>>
}