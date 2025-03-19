package com.example.langapp.data.repositories

import com.example.langapp.data.database.CategoryDao
import com.example.langapp.data.entities.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories().flowOn(Dispatchers.IO)

    fun getCategoryById(id: Int): Flow<CategoryEntity?> = categoryDao.getCategoryById(id).flowOn(Dispatchers.IO)

    fun getCategoriesByName(name: String): Flow<List<CategoryEntity>> = categoryDao.getCategoriesByName(name).flowOn(Dispatchers.IO)

    fun getCategoriesByTransl(transl: String): Flow<List<CategoryEntity>> = categoryDao.getCategoriesByTransl(transl).flowOn(Dispatchers.IO)

    fun getCategoriesByTranscr(transcr: String): Flow<List<CategoryEntity>> = categoryDao.getCategoriesByTranscr(transcr).flowOn(Dispatchers.IO)

    fun getCategoriesSortedByName(): Flow<List<CategoryEntity>> = categoryDao.getCategoriesSortedByName().flowOn(Dispatchers.IO)

    fun getCategoriesSortedByTransl(): Flow<List<CategoryEntity>> = categoryDao.getCategoriesSortedByTransl().flowOn(Dispatchers.IO)

    fun getCategoriesSortedByTranscr(): Flow<List<CategoryEntity>> = categoryDao.getCategoriesSortedByTranscr().flowOn(Dispatchers.IO)
}