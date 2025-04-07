package com.example.langapp.ui

import com.example.langapp.data.Category

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)