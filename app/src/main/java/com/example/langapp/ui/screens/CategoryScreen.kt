package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.components.CategoryCard
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.CategoryUiState

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    onNavigateToWordList: (Int) -> Unit
) {
    val categoriesUiState by categoryViewModel.categoryUiState.collectAsState()
    CommonScreen(
        topBar = {
            TopBar(title = "Категории")
        },
        content = { innerPadding ->
            CategoryList(
                categoriesUiState = categoriesUiState,
                onNavigateToWordList = onNavigateToWordList,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )
}

@Composable
fun CategoryList(
    categoriesUiState: CategoryUiState,
    onNavigateToWordList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(categoriesUiState.categories) { category ->
            CategoryCard(
                category = category,
                onCardClick = { onNavigateToWordList(category.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}