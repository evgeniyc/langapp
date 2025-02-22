package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.ui.viewmodels.WordViewModelFactory

@Composable
fun WordListScreen(categoryId: Int, wordViewModelFactory: WordViewModelFactory, navController: NavController) {
    Log.d("WordListScreen", "WordListScreen")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow) // Добавили желтый фон для Column
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Список слов больше:",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
        Log.d("WordListScreen", "Before Button")
        Button(
            onClick = {
                navController.navigate("learning/$categoryId")
            },
            modifier = Modifier
                .fillMaxWidth() // Занимает всю доступную ширину
                .height(50.dp) // Устанавливает высоту 50dp
                .padding(16.dp), // Добавляет отступы со всех сторон 16dp
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Устанавливает красный цвет фона
        ) {
            Text(
                text = "Начать",
                fontSize = 24.sp, // Устанавливает размер текста 24sp
                color = Color.White, // Устанавливает белый цвет текста
                fontWeight = FontWeight.Bold // Делает текст жирным
            )
        }
        Log.d("WordListScreen", "After Button")
    }
}