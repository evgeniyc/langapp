package com.example.langapp.ui

import com.example.langapp.data.entities.CategoryEntity

data class CategoryUiState(
    val categoryList: List<CategoryEntity> = emptyList()
)