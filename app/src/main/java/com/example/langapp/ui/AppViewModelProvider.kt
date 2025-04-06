package com.example.langapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.langapp.LangApplication
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
            CategoryViewModel(
                categoryRepository = application.appContainer.categoryRepository,
                wordDao = application.appContainer.wordDao
            )
        }
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
            val savedStateHandle = this.createSavedStateHandle()
            WordViewModel(application.appContainer.wordRepository, savedStateHandle)
        }
    }
}