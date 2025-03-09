package com.example.langapp.ui

import com.example.langapp.data.entities.Category

data class CategoryUiState(
    val categoryList: List<Category> = emptyList()
)