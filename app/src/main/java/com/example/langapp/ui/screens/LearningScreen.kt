package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.langapp.ui.viewmodels.WordViewModelFactory

@Composable
fun LearningScreen(
    categoryId: Int,
    wordViewModelFactory: WordViewModelFactory,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Learning Screen")
    }
}