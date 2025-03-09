package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.ui.CategoryUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryViewModel(categoryRepository: CategoryRepository) : ViewModel() {
    val categoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getAllCategories().map {
            Log.d("CategoryListViewModel", "Categories loaded: ${it.size}") // Добавлено логирование
            CategoryUiState(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CategoryUiState()
            )

    init {
        Log.d("CategoryListViewModel", "ViewModel initialized") // Добавлено логирование
    }
}