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
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    wordViewModel: WordViewModel,
    onNavigateToWordList: (Int) -> Unit
) {
    Log.d("CategoryScreen", "CategoryScreen: called")
    val categories by categoryViewModel.categoryUiState.collectAsState()
    Log.d("CategoryScreen", "CategoryScreen: categories.size = ${categories.categoryList.size}")

    CommonScreen(
        topBar = {
            TopBar(title = "Категории")
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(categories.categoryList) { category ->
                    val progress by wordViewModel.getProgressForCategory(category.id)
                        .collectAsState(initial = 0f)
                    CategoryCard(
                        category = category,
                        progress = progress,
                        onCardClick = { onNavigateToWordList(category.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}