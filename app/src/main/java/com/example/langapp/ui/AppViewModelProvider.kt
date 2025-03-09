package com.example.langapp.ui

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.langapp.LangApplication
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

object AppViewModelProvider {
    private const val TAG = "AppViewModelProvider"
    fun Factory(catId: Int = 0): ViewModelProvider.Factory {
        Log.d(TAG, "Factory: start, catId = $catId")
        val result = viewModelFactory {
            initializer {
                Log.d(TAG, "CategoryViewModel: start")
                // Получаем экземпляр LangApplication через CreationExtras
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
                // Используем application.container для доступа к зависимостям
                val result = CategoryViewModel(application.appContainer.categoryRepository)
                Log.d(TAG, "CategoryViewModel: end")
                result
            }
            initializer {
                Log.d(TAG, "WordViewModel: start")
                // Получаем экземпляр LangApplication через CreationExtras
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LangApplication
                // 1. Передаем SavedStateHandle
                val savedStateHandle = this.createSavedStateHandle()
                // 2. Сохраняем catId в SavedStateHandle
                savedStateHandle[WordViewModel.CATEGORY_ID] = catId
                val result = WordViewModel(application.appContainer.wordRepository, savedStateHandle)
                Log.d(TAG, "WordViewModel: end")
                result
            }
        }
        Log.d(TAG, "Factory: end")
        return result
    }
}