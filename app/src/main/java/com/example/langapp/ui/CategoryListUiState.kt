package com.example.langapp.ui

import com.example.langapp.data.entities.Category

data class CategoryListUiState(
    val categoryList: List<Category> = emptyList()
)