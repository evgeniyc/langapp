package com.example.langapp.data

import kotlinx.coroutines.flow.Flow
import com.example.langapp.data.Category

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

    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    fun getCategoryById(id: Int): Flow<Category> {
        return categoryDao.getCategoryById(id)
    }
}