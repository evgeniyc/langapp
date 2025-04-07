package com.example.langapp.data.repositories

import com.example.langapp.data.database.CategoryTimeDao
import com.example.langapp.data.entities.CategoryTimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryTimeRepository @Inject constructor(private val categoryTimeDao: CategoryTimeDao) {
    fun getCategoryTimeById(categoryId: Int): Flow<CategoryTimeEntity?> {
        return categoryTimeDao.getCategoryTimeById(categoryId)
    }

    suspend fun updateCategoryTime(categoryTime: CategoryTimeEntity) {
        categoryTimeDao.updateCategoryTime(categoryTime)
    }

    suspend fun insertCategoryTime(categoryTime: CategoryTimeEntity) {
        categoryTimeDao.insertCategoryTime(categoryTime)
    }

    fun getAllCategoryTime(): Flow<List<CategoryTimeEntity>> {
        return categoryTimeDao.getAllCategoryTime()
    }
}