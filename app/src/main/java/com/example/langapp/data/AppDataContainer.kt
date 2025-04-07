package com.example.langapp.data

import android.content.Context
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.database.WordDao
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.CategoryTimeRepository
import com.example.langapp.data.repositories.WordRepository

class AppDataContainer(private val context: Context) : AppContainer {
    private val database: LangDatabase by lazy { LangDatabase.getDatabase(context) }

    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(database.categoryDao())
    }

    override val wordRepository: WordRepository by lazy {
        WordRepository(database.wordDao())
    }

    override val wordDao: WordDao by lazy {
        database.wordDao()
    }
    override val categoryTimeRepository: CategoryTimeRepository by lazy { // Добавили
        CategoryTimeRepository(database.categoryTimeDao())
    }
}