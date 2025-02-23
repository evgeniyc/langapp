package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Word
import com.example.langapp.ui.WordListViewModel

@Composable
fun WordListScreen(
    catId: Int, // Изменен параметр
    wordListViewModel: WordListViewModel, // Изменен параметр
    navController: NavController
) {
    val words by wordListViewModel.getWordsByCategoryId(catId).collectAsState(initial = emptyList()) // Получаем данные из WordListViewModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Список слов",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 92.dp, bottom = 16.dp)
        )
        // Список слов
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(words) { word -> // Отображаем реальные слова
                WordItem(word = word) // Добавлен WordItem
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Button(
            onClick = {
                navController.navigate("learning/$catId") // Исправлена навигация
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Начать",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WordItem(word: Word) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Исправлено: fillMaxSize -> fillMaxWidth
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = word.name, style = MaterialTheme.typography.titleMedium)
            Text(text = word.transl, style = MaterialTheme.typography.bodyMedium)
            Text(text = word.transcr, style = MaterialTheme.typography.bodySmall)
        }
    }
}