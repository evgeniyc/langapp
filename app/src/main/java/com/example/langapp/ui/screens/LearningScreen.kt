package com.example.langapp.ui.screens

import android.util.Log
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Word
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.components.LearningCard
import com.example.langapp.ui.components.LearningControls
import com.example.langapp.ui.components.LearningProgress
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.components.Screen
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.viewmodels.WordViewModel
import kotlin.math.roundToInt

@Composable
fun LearningScreen(
    wordViewModel: WordViewModel,
    catId: Int,
    navController: NavController,
    filter: WordFilter
) {
    wordViewModel.savedStateHandle[WordViewModel.CATEGORY_ID] = catId
    val words by wordViewModel.wordUiState.collectAsState()


    LaunchedEffect(key1 = filter) {
        wordViewModel.updateFilter(filter)
    }

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
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

    // Функция для обновления слова в БД
    fun updateWord(word: Word) {
        wordViewModel.updateWord(word)
    }

    Screen(
        topBar = { TopBar(title = "Обучение") },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
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
                        },
                        key = currentWordIndex,
                        onUpdateWord = { updatedWord ->
                            updateWord(updatedWord)
                        },
                        modifier = Modifier
                            .offset { IntOffset(offsetX.roundToInt(), 0) }
                            .draggable(
                                orientation =
                                Orientation.Horizontal,
                                state =
                                rememberDraggableState { delta ->
                                    offsetX += delta
                                },
                                onDragStopped = { velocity ->
                                    if (offsetX > 100) {
                                        currentWordIndex =
                                            (currentWordIndex - 1 + words.wordList.size) % words.wordList.size
                                    } else if (offsetX < -100) {
                                        currentWordIndex =
                                            (currentWordIndex + 1) % words.wordList.size
                                    }
                                    offsetX = 0f
                                    isFlipped = false
                                }

                            )
                    )
                }
            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = {
                    navController.navigate(Screen.WordList.createRoute(catId))

                },
                onRightClick = {
                    navController.navigate(Screen.CategoryList.route)

                },
                modifier = Modifier.padding(horizontal = 16.dp),
                leftText = "Назад",
                rightText = "Категории"
            )


            /*LearningControls(
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
                    navController.popBackStack(Screen.CategoryList.route, inclusive = false)
                    navController.navigate(Screen.CategoryList.route)
                }
            )*/
        }
    )
}