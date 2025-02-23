package com.example.langapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Word
import com.example.langapp.ui.WordListViewModel

@Composable
fun LearningScreen(
    catId: Int, // Изменен параметр
    wordListViewModel: WordListViewModel, // Изменен параметр
    navController: NavController // Изменен параметр
) {
    val words by wordListViewModel.getWordsByCategoryId(catId).collectAsState(initial = emptyList()) // Получаем данные из WordListViewModel

    // Текущее слово
    var currentWordIndex by remember { mutableStateOf(0) }
    // Проверяем, что список не пустой
    if (words.isEmpty()) {
        Text(text = "В этой категории пока нет слов")
        return
    }
    val currentWord: Word = words[currentWordIndex]

    // Состояние переворота
    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Счетчик
        Text(
            text = "${currentWordIndex + 1}/${words.size}",
            modifier = Modifier
                .padding(top = 92.dp)
                .align(Alignment.TopEnd)
                .padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Карточка
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { isFlipped = !isFlipped }
                    .graphicsLayer { this.rotationY = rotationY; cameraDistance = 12f * density },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentWord.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.graphicsLayer {
                            alpha = if (rotationY < 90f) 1f else 0f
                        }
                    )
                    Text(
                        text = currentWord.transl,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .graphicsLayer { this.rotationY = 180f }
                            .graphicsLayer {
                                alpha = if (rotationY > 90f) 1f else 0f
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Кнопки навигации
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        currentWordIndex = (currentWordIndex - 1 + words.size) % words.size
                        isFlipped = false
                    }
                ) {
                    Text(text = "Предыдущее")
                }
                Button(
                    onClick = {
                        currentWordIndex = (currentWordIndex + 1) % words.size
                        isFlipped = false
                    }
                ) {
                    Text(text = "Следующее")
                }
            }
        }
    }
}