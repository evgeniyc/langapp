package com.example.langapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.components.LearningCard
import com.example.langapp.ui.components.LearningProgress
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.viewmodels.WordViewModel
import kotlin.math.roundToInt
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.example.langapp.navigation.Screen

@Composable
fun LearningScreen(
    wordViewModel: WordViewModel,
    onNavigateToWordList: () -> Unit,
    onNavigateToCategoryList: () -> Unit
) {
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var isFlipped by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    var cardWidthPx by remember { mutableStateOf(0) }
    val cardWidthDp = with(density) { cardWidthPx.toDp() }

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
                            AnimatedContent(
                                targetState = wordUiState.currentWord,
                                transitionSpec = {
                                    if (targetState.id > initialState.id) {
                                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                                            animationSpec = tween(300)
                                        )
                                    } else {
                                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                                            animationSpec = tween(300)
                                        )
                                    }
                                }, label = ""
                            ) { currentWord ->
                                LearningCard(
                                    currentWord = currentWord,
                                    isFlipped = isFlipped,
                                    onCardClick = { isFlipped = !isFlipped },
                                    onLearnedClicked = { word ->
                                        wordViewModel.onLearnedClicked(word)
                                    },
                                    onImportantClicked = { word ->
                                        wordViewModel.onImportantClicked(word)
                                    },
                                    modifier = Modifier
                                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                                        .onGloballyPositioned { coordinates ->
                                            cardWidthPx = coordinates.size.width
                                        }
                                        .pointerInput(Unit) {
                                            detectHorizontalDragGestures { change, dragAmount ->
                                                change.consume()
                                                offsetX += dragAmount
                                            }
                                        }
                                        .graphicsLayer {
                                            val targetOffsetX = when {
                                                offsetX > cardWidthPx / 2 -> cardWidthPx.toFloat()
                                                offsetX < -cardWidthPx / 2 -> -cardWidthPx.toFloat()
                                                else -> 0f
                                            }
                                            if (offsetX != targetOffsetX) {
                                                offsetX = targetOffsetX
                                                if (offsetX > 0) {
                                                    wordViewModel.onSwipe(true)
                                                } else {
                                                    wordViewModel.onSwipe(false)
                                                }
                                                offsetX = 0f
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = onNavigateToWordList,
                onRightClick = onNavigateToCategoryList,
                leftText = "Слова",
                rightText = "Категории",
            )
        }
    )
}