package com.example.langapp.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.components.LearningCard
import com.example.langapp.ui.components.LearningProgress
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.viewmodels.WordViewModel
import kotlin.math.roundToInt

@Composable
fun LearningScreen(
    wordViewModel: WordViewModel, catId: Int, navController: NavController
) {
    wordViewModel.savedStateHandle[WordViewModel.CATEGORY_ID] = catId
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var isFlipped by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }

    CommonScreen(
        topBar = { TopBar(title = "Обучение") },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (wordUiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    if (wordUiState.words.isEmpty()) {
                        Text(text = "Список с этими параметрами пуст")
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(48.dp))
                            LearningProgress(
                                currentWordIndex = wordUiState.index,
                                wordsSize = wordUiState.size,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            wordUiState.currentWord?.let {
                                LearningCard(
                                    currentWord = it,
                                    isFlipped = isFlipped,
                                    onCardClick = {
                                        isFlipped = !isFlipped
                                    },
                                    onLearnedClicked = { word ->
                                        wordViewModel.onLearnedClicked(word)
                                    },
                                    onImportantClicked = { word ->
                                        wordViewModel.onImportantClicked(word)
                                    },
                                    modifier = Modifier
                                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                                        .draggable(orientation = Orientation.Horizontal,
                                            state = rememberDraggableState { delta ->
                                                offsetX += delta
                                            },
                                            onDragStopped = { velocity ->
                                                if (offsetX > 100) {
                                                    wordViewModel.onSwipe(false)
                                                } else if (offsetX < -100) {
                                                    wordViewModel.onSwipe(true)
                                                }
                                                offsetX = 0f
                                                isFlipped = false
                                            }

                                        )
                                )
                            } ?: Text(text = "Список с этими параметрами пуст")
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = {
                    navController.navigate(Screen.WordList.createRoute(catId, wordUiState.mode))

                },
                onRightClick = {
                    navController.navigate(Screen.CategoryList.route)

                },
                leftText = "Назад",
                rightText = "Категории",
                catId = catId,
                mode = wordUiState.mode // Используем wordUiState.mode
            )
        }
    )
}