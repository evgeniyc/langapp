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
import com.example.langapp.ui.viewmodels.LearningViewModel
import com.example.langapp.ui.viewmodels.WordListViewModel

@Composable
fun LearningScreen(
    wordListViewModel: WordListViewModel,
    learningViewModel: LearningViewModel,
    catId: Int,
    navController: NavController
) {
    Log.d("LearningScreen", "LearningScreen() started, catId = $catId")
    val words by wordListViewModel.wordListUiState.collectAsState()

    LaunchedEffect(key1 = catId) {
        Log.d("LearningScreen", "LaunchedEffect started, catId = $catId")
        //wordListViewModel.getWordsByCategoryId(catId) // Мы убрали этот вызов!
        Log.d("LearningScreen", "getWordsByCategoryId() not called, catId = $catId")
    }

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    if (words.wordList.isEmpty()) {
        Log.d("LearningScreen", "wordList is empty")
        Text(
            text = "В этой категории пока нет слов",
            modifier = Modifier.padding(top = 92.dp)
        )
        Log.d("LearningScreen", "Text 'В этой категории пока нет слов' displayed")
        return
    }

    Log.d("LearningScreen", "wordList is not empty")
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 42.dp, end = 16.dp, bottom = 0.dp),
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
            Log.d("LearningScreen", "Text 'Обучение' displayed")
            LearningProgress(
                learnedWordsCount = words.learnedWordsCount,
                wordsSize = words.wordList.size,
            )
            Log.d("LearningScreen", "LearningProgress called")
            Spacer(modifier = Modifier.height(16.dp))
            LearningCard(
                currentWord = words.wordList[currentWordIndex],
                isFlipped = isFlipped,
                onCardClick = {
                    Log.d("LearningScreen", "LearningCard clicked")
                    isFlipped = !isFlipped
                },
                onIsLearnedChange = {
                    Log.d("LearningScreen", "onIsLearnedChange called")
                    learningViewModel.updateWordIsLearned(it)
                    Log.d("LearningScreen", "updateWordIsLearned() called")
                },
                onIsImportantChange = {
                    Log.d("LearningScreen", "onIsImportantChange called")
                    learningViewModel.updateWordIsImportant(it)
                    Log.d("LearningScreen", "updateWordIsImportant() called")
                }
            )
            Log.d("LearningScreen", "LearningCard called")
            LearningControls(
                onPreviousClick = {
                    Log.d("LearningScreen", "onPreviousClick called")
                    currentWordIndex = (currentWordIndex - 1 + words.wordList.size) % words.wordList.size
                    isFlipped = false
                    Log.d("LearningScreen", "currentWordIndex = $currentWordIndex, isFlipped = $isFlipped")
                },
                onNextClick = {
                    Log.d("LearningScreen", "onNextClick called")
                    currentWordIndex = (currentWordIndex + 1) % words.wordList.size
                    isFlipped = false
                    Log.d("LearningScreen", "currentWordIndex = $currentWordIndex, isFlipped = $isFlipped")
                }
            )
            Log.d("LearningScreen", "LearningControls called")
        }
    }
    Log.d("LearningScreen", "LearningScreen() finished, catId = $catId")
}