package com.example.langapp.data
import com.example.langapp.data.database.CategoryDao
import com.example.langapp.data.database.LangDatabase
import com.example.langapp.data.database.WordDao
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository

interface AppContainer {
    val categoryRepository: CategoryRepository
    val wordRepository: WordRepository
    val wordDao: WordDao
}

class DefaultAppContainer(private val database: LangDatabase) : AppContainer {
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