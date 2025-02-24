package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.repositories.CategoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryListViewModel(categoryRepository: CategoryRepository) : ViewModel() {
    val categoryListUiState: StateFlow<CategoryListUiState> =
        categoryRepository.getAllCategories().map {
            Log.d("CategoryListViewModel", "Categories loaded: ${it.size}") // Добавлено логирование
            CategoryListUiState(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CategoryListUiState()
            )

    init {
        Log.d("CategoryListViewModel", "ViewModel initialized") // Добавлено логирование
    }
}