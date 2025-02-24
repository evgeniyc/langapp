package com.example.langapp.ui.viewmodels

import com.example.langapp.data.entities.Category

data class CategoryListUiState(
    val categoryList: List<Category> = emptyList()
)