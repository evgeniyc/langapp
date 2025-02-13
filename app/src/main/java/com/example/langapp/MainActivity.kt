package com.example.langapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.langapp.data.AppDatabase
import com.example.langapp.data.CategoryRepository
import com.example.langapp.ui.screens.CategoryListScreen
import com.example.langapp.ui.theme.LangAppTheme
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.CategoryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        Log.d("MainActivity", "onCreate: categoryDao created") // Добавлено!
        val categoryRepository = CategoryRepository(categoryDao)
        Log.d("MainActivity", "onCreate: categoryRepository created") // Добавлено!
        val categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        setContent {
            Log.d("MainActivity", "onCreate: setContent called") // Добавлено!
            LangAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val categoryViewModel: CategoryViewModel =
                        viewModel(factory = categoryViewModelFactory) // Изменено здесь!
                    CategoryListScreen(categoryViewModel)
                }
            }
        }
    }
}