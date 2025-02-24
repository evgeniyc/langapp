package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.ui.viewmodels.WordListViewModel

@Composable
fun LearningScreen(
    wordListViewModel: WordListViewModel,
    categoryId: Int,
    navController: NavController
) {
    val words by wordListViewModel.wordListUiState.collectAsState()

    LaunchedEffect(key1 = categoryId) {
        wordListViewModel.getWordsByCategoryId(categoryId)
    }

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    if (words.wordList.isEmpty()) {
        Text(
            text = "В этой категории пока нет слов",
            modifier = Modifier.padding(top = 92.dp)
        )
        return
    }

    Log.d(
        "LearningScreen",
        "Recomposition: currentWordIndex = $currentWordIndex, isFlipped = $isFlipped"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 42.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Обучение",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LearningProgress(
                currentWordIndex = currentWordIndex,
                wordsSize = words.wordList.size,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LearningCard(
                currentWord = words.wordList[currentWordIndex],
                isFlipped = isFlipped,
                onCardClick = {
                    isFlipped = !isFlipped
                    Log.d("LearningScreen", "Card clicked: isFlipped = $isFlipped")
                },
                key = currentWordIndex
            )
            LearningControls(
                onPreviousClick = {
                    currentWordIndex = (currentWordIndex - 1 + words.wordList.size) % words.wordList.size
                    isFlipped = false
                    Log.d(
                        "LearningScreen",
                        "Previous button clicked: currentWordIndex = $currentWordIndex, isFlipped = $isFlipped"
                    )
                },
                onNextClick = {
                    currentWordIndex = (currentWordIndex + 1) % words.wordList.size
                    isFlipped = false
                    Log.d(
                        "LearningScreen",
                        "Next button clicked: currentWordIndex = $currentWordIndex, isFlipped = $isFlipped"
                    )
                },
                onCategoryClick = {
                    navController.popBackStack(com.example.langapp.navigation.Screen.CategoryList.route, inclusive = false)
                    navController.navigate(com.example.langapp.navigation.Screen.CategoryList.route)
                }
            )
        }
    }
}