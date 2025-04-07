package com.example.langapp.data.database

import android.content.Context
import android.util.Log
import com.example.langapp.constants.Categories
import com.example.langapp.constants.AllWords
import com.example.langapp.data.entities.CategoryTimeEntity
import com.example.langapp.data.entities.WordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

object DatabaseInitializer {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initialize(context: Context) {
        Log.d("DatabaseInitializer", "initialize called")
        applicationScope.launch {
            val database = LangDatabase.getDatabase(context)
            val categoryDao = database.categoryDao()
            val wordDao = database.wordDao()
            val categoryTimeDao = database.categoryTimeDao()

            // Проверка и добавление категорий
            Log.d("DatabaseInitializer", "Checking if categories exist")
            val categories = try {
                categoryDao.getAllCategories().firstOrNull()
            } catch (e: Exception) {
                Log.e("DatabaseInitializer", "Error getting categories", e)
                null
            }
            Log.d("DatabaseInitializer", "Categories found: ${categories?.size ?: 0}")
            if (categories.isNullOrEmpty()) {
                Log.d("DatabaseInitializer", "No categories found, inserting categories")
                Categories.categoryList.forEach { category ->
                    Log.d("DatabaseInitializer", "Inserting category: ${category.name}")
                    try {
                        categoryDao.insertCategory(category)
                        Log.d("DatabaseInitializer", "Category inserted: ${category.name}")
                    } catch (e: Exception) {
                        Log.e("DatabaseInitializer", "Error inserting category: ${category.name}", e)
                    }
                }
                Log.d("DatabaseInitializer", "Finished inserting categories")
            } else {
                Log.d("DatabaseInitializer", "Categories already exist")
            }

            // Проверка и добавление времени для категорий
            Log.d("DatabaseInitializer", "Checking if category times exist")
            val categoryTimes = try {
                categoryTimeDao.getAllCategoryTime().firstOrNull()
            } catch (e: Exception) {
                Log.e("DatabaseInitializer", "Error getting category times", e)
                null
            }
            Log.d("DatabaseInitializer", "Category times found: ${categoryTimes?.size ?: 0}")
            if (categoryTimes.isNullOrEmpty()) {
                Log.d("DatabaseInitializer", "No category times found, inserting category times")
                Categories.categoryList.forEach { category ->
                    Log.d("DatabaseInitializer", "Inserting category time for: ${category.name}")
                    try {
                        categoryTimeDao.insertCategoryTime(CategoryTimeEntity(categoryId = category.id))
                        Log.d("DatabaseInitializer", "Category time inserted for: ${category.name}")
                    } catch (e: Exception) {
                        Log.e("DatabaseInitializer", "Error inserting category time for: ${category.name}", e)
                    }
                }
                Log.d("DatabaseInitializer", "Finished inserting category times")
            } else {
                Log.d("DatabaseInitializer", "Category times already exist")
            }

            // Проверка и добавление/удаление слов
            Log.d("DatabaseInitializer", "Checking if words exist")
            val wordsInDb = try {
                wordDao.getAllWords().firstOrNull() ?: emptyList()
            } catch (e: Exception) {
                Log.e("DatabaseInitializer", "Error getting words", e)
                emptyList()
            }
            Log.d("DatabaseInitializer", "Words found: ${wordsInDb.size}")

            val wordsInConstants = AllWords.allWords
            val wordsToAdd = mutableListOf<WordEntity>()
            val wordsToDelete = mutableListOf<WordEntity>()

            // Проверка на добавление
            wordsInConstants.forEach { wordInConstants ->
                if (!wordsInDb.any { it.name == wordInConstants.name }) {
                    wordsToAdd.add(wordInConstants)
                }
            }
            // Проверка на удаление
            wordsInDb.forEach { wordInDb ->
                if (!wordsInConstants.any { it.name == wordInDb.name }) {
                    wordsToDelete.add(wordInDb)
                }
            }

            // Добавление и удаление слов
            addOrDeleteWords(wordDao, wordsToAdd, wordsToDelete)
        }
    }

    private suspend fun addOrDeleteWords(
        wordDao: com.example.langapp.data.database.WordDao,
        wordsToAdd: List<WordEntity>,
        wordsToDelete: List<WordEntity>
    ) {
        // Добавление
        if (wordsToAdd.isNotEmpty()) {
            Log.d("DatabaseInitializer", "Found ${wordsToAdd.size} words to add")
            wordsToAdd.forEach { word ->
                Log.d("DatabaseInitializer", "Inserting word: ${word.name}")
                try {
                    wordDao.insertWord(word)
                    Log.d("DatabaseInitializer", "WordEntity inserted: ${word.name}")
                } catch (e: Exception) {
                    Log.e("DatabaseInitializer", "Error inserting word: ${word.name}", e)
                }
            }
            Log.d("DatabaseInitializer", "Finished inserting words")
        } else {
            Log.d("DatabaseInitializer", "All words already exist")
        }
        // Удаление
        if (wordsToDelete.isNotEmpty()) {
            Log.d("DatabaseInitializer", "Found ${wordsToDelete.size} words to delete")
            wordsToDelete.forEach { word ->
                Log.d("DatabaseInitializer", "Deleting word: ${word.name}")
                try {
                    wordDao.deleteWord(word)
                    Log.d("DatabaseInitializer", "WordEntity deleted: ${word.name}")
                } catch (e: Exception) {
                    Log.e("DatabaseInitializer", "Error deleting word: ${word.name}", e)
                }
            }
            Log.d("DatabaseInitializer", "Finished deleting words")
        } else {
            Log.d("DatabaseInitializer", "No words to delete")
        }
    }
}