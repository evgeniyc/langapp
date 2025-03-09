package com.example.langapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Category
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.components.CategoryCard
import com.example.langapp.ui.components.Screen
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.viewmodels.CategoryViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun CategoryScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel
) {
    val categories by categoryViewModel.categoryUiState.collectAsState()

    Screen(
        topBar = { TopBar(title = "Категории") },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(categories.categoryList) { category ->
                    CategoryCard(category = category, navController = navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}
