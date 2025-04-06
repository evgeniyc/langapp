package com.example.langapp.ui

import com.example.langapp.data.Category
import com.example.langapp.data.entities.CategoryEntity

data class CategoryUiState(
    val categories: List<Category> = emptyList()
)