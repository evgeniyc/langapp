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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.langapp.ui.components.CategoryCard
import com.example.langapp.ui.components.Screen
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun CategoryScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    wordViewModel: WordViewModel = hiltViewModel()
) {
    Log.d("CategoryScreen", "CategoryScreen: start")
    val categories by categoryViewModel.categoryUiState.collectAsState()
    Log.d("CategoryScreen", "CategoryScreen: categories = $categories")

    Screen(
        topBar = {
            Log.d("CategoryScreen", "Screen: topBar: start")
            TopBar(title = "Категории")
            Log.d("CategoryScreen", "Screen: topBar: end")
        },
        content = { innerPadding ->
            Log.d("CategoryScreen", "Screen: content: start")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Log.d("CategoryScreen", "LazyColumn: start")
                items(categories.categoryList) { category ->
                    Log.d("CategoryScreen", "items: start, category = $category")
                    val progress by wordViewModel.getProgressForCategory(category.id)
                        .collectAsState(initial = 0f)
                    Log.d("CategoryScreen", "items: progress = $progress")
                    CategoryCard(
                        category = category,
                        navController = navController,
                        progress = progress
                    )
                    Log.d("CategoryScreen", "items: end")
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Log.d("CategoryScreen", "LazyColumn: end")
            }
            Log.d("CategoryScreen", "Screen: content: end")
        }
    )
    Log.d("CategoryScreen", "CategoryScreen: end")
}