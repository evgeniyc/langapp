package com.example.langapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.langapp.LangApplication
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

object AppViewModelProvider {
    fun Factory(catId: Int = 0): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            // Получаем экземпляр LangApplication через CreationExtras
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
            // Используем application.container для доступа к зависимостям
            CategoryViewModel(application.appContainer.categoryRepository)
        }
        initializer {
            // Получаем экземпляр LangApplication через CreationExtras
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
            // 1. Передаем SavedStateHandle
            val savedStateHandle = this.createSavedStateHandle()
            // 2. Сохраняем catId в SavedStateHandle
            savedStateHandle[WordViewModel.CATEGORY_ID] = catId
            WordViewModel(application.appContainer.wordRepository, savedStateHandle)
        }
    }
}