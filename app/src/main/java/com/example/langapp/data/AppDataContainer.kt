package com.example.langapp.data

import android.content.Context
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.database.WordDao

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
}