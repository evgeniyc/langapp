package com.example.langapp.data.repositories

import com.example.langapp.data.dao.CategoryDao
import com.example.langapp.data.entities.Category
import kotlinx.coroutines.Dispatchers
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
       emitAll(categoryDao.getAllCategories())
    }.flowOn(Dispatchers.IO)

    fun getCategoryById(id: Int): Flow<Category?> = flow {
        emitAll(categoryDao.getCategoryById(id))
    }.flowOn(Dispatchers.IO)

    fun getCategoriesByName(name: String): Flow<List<Category>> = categoryDao.getCategoriesByName(name).flowOn(Dispatchers.IO)

    fun getCategoriesByTransl(transl: String): Flow<List<Category>> = categoryDao.getCategoriesByTransl(transl).flowOn(Dispatchers.IO)

    fun getCategoriesByTranscr(transcr: String): Flow<List<Category>> = categoryDao.getCategoriesByTranscr(transcr).flowOn(Dispatchers.IO)

    fun getCategoriesSortedByName(): Flow<List<Category>> = categoryDao.getCategoriesSortedByName().flowOn(Dispatchers.IO)

    fun getCategoriesSortedByTransl(): Flow<List<Category>> = categoryDao.getCategoriesSortedByTransl().flowOn(Dispatchers.IO)

    fun getCategoriesSortedByTranscr(): Flow<List<Category>> = categoryDao.getCategoriesSortedByTranscr().flowOn(Dispatchers.IO)
}