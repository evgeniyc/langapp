package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.langapp.data.Category
import com.example.langapp.ui.viewmodels.CategoryViewModel

@Composable
fun CategoryListScreen(categoryViewModel: CategoryViewModel = viewModel()) {
    Log.d("CategoryScreen", "CategoryListScreen called")
    val categories by categoryViewModel.allCategories.observeAsState(initial = emptyList())
    Log.d("CategoryScreen", "Categories: $categories")
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
        text = "Список категорий:",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
    )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(categories, key = { category: Category -> category.id }) { category ->
                CategoryItem(category = category)
                Spacer(modifier = Modifier.height(8.dp)) // Добавляем отступ между карточками
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Занимать всю ширину
            .padding(8.dp) // Добавить отступы вокруг карточки
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Добавить отступы внутри карточки
        ) {
            Text(text = "${category.name_ru} - ${category.name_de}")
        }
    }
}