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

object DatabaseInitializer {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun initialize(context: Context) {
        Log.d("DatabaseInitializer", "initialize called")
        applicationScope.launch {
            val database = LangDatabase.getDatabase(context)
            val categoryDao = database.categoryDao()
            val wordDao = database.wordDao() // Получаем WordDao

            // Проверяем, есть ли уже категории в базе данных
            val categories = categoryDao.getAllCategories().firstOrNull()
            if (categories.isNullOrEmpty()) {
                // Если категорий нет, добавляем их
                Categories.categoryList.forEach { category ->
                    categoryDao.insertCategory(category)
                }
            }

            // Проверяем, есть ли уже слова в базе данных
            val words = wordDao.getAllWords().firstOrNull()
            if (words.isNullOrEmpty()) {
                // Если слов нет, добавляем их
                Words.wordList.forEach { word ->
                    wordDao.insertWord(word)
                }
            }
        }
    }
}