package com.example.langapp.data

import android.content.Context
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository

class AppDataContainer(private val context: Context) : AppContainer {
    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(LangDatabase.getDatabase(context).categoryDao())
    }
    override val wordRepository: WordRepository by lazy {
        WordRepository(LangDatabase.getDatabase(context).wordDao())
    }
}