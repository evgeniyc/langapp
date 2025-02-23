package com.example.langapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.langapp.LangApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CategoryListViewModel(
                langApplication().appContainer.categoryRepository
            )
        }
        initializer {
            WordListViewModel(
                langApplication().appContainer.wordRepository
            )
        }
    }
}

fun CreationExtras.langApplication(): LangApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication)