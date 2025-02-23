package com.example.langapp.data

import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository

interface AppContainer {
    val categoryRepository: CategoryRepository
    val wordRepository: WordRepository
}