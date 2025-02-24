package com.example.langapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.langapp.LangApplication
import com.example.langapp.ui.viewmodels.CategoryListViewModel
import com.example.langapp.ui.viewmodels.WordListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            return@initializer CategoryListViewModel(
                langApplication().appContainer.categoryRepository
            ) as CategoryListViewModel
        }
        initializer {
            return@initializer WordListViewModel(
                langApplication().appContainer.wordRepository,
                langApplication().appContainer.categoryRepository
            ) as WordListViewModel
        }
    }
}

fun CreationExtras.langApplication(): LangApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication)