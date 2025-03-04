package com.example.langapp.data.database

import android.content.Context
import android.util.Log
import com.example.langapp.constants.Categories
import com.example.langapp.constants.Words
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object DatabaseInitializer {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initialize(context: Context) {
        Log.d("DatabaseInitializer", "initialize called")
        runBlocking {
            val job = applicationScope.launch {
                val database = LangDatabase.getDatabase(context)
                val categoryDao = database.categoryDao()
                val wordDao = database.wordDao()

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

                Log.d("DatabaseInitializer", "Checking if words exist")
                val words = try {
                    wordDao.getAllWords().firstOrNull()
                } catch (e: Exception) {
                    Log.e("DatabaseInitializer", "Error getting words", e)
                    null
                }
                Log.d("DatabaseInitializer", "Words found: ${words?.size ?: 0}")
                if (words.isNullOrEmpty()) {
                    Log.d("DatabaseInitializer", "No words found, inserting words")
                    Words.wordList.forEach { word ->
                        Log.d("DatabaseInitializer", "Inserting word: ${word.name}")
                        try {
                            wordDao.insertWord(word)
                            Log.d("DatabaseInitializer", "Word inserted: ${word.name}")
                        } catch (e: Exception) {
                            Log.e("DatabaseInitializer", "Error inserting word: ${word.name}", e)
                        }
                    }
                    Log.d("DatabaseInitializer", "Finished inserting words")
                } else {
                    Log.d("DatabaseInitializer", "Words already exist")
                }
            }
            job.join()
        }
    }
}