package com.example.langapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    fun getAllCategories(): Flow<List<Category>> = flow {
        delay(1000) // Задержка перед получением данных
        emitAll(categoryDao.getAllCategories())
    }.flowOn(Dispatchers.IO)

    fun getCategoryById(id: Int): Flow<Category> = flow {
        delay(1000) // Задержка перед получением данных
        emitAll(categoryDao.getCategoryById(id))
    }.flowOn(Dispatchers.IO)
}