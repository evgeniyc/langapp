package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.components.AnimatedLearningCard
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.components.WordTopBar
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun LearningScreen(
    wordViewModel: WordViewModel,
    onNavigateToWordList: () -> Unit,
    onNavigateToCategoryList: () -> Unit
) {
    Log.d(TAG, "LearningScreen: called")
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }
    var mode by remember { mutableStateOf(WordFilter.NOT_LEARNED.ordinal) }

    LaunchedEffect(key1 = wordUiState.catId) {
        wordViewModel.loadWords()
    }
    CommonScreen(
        topBar = {
            WordTopBar(
                title = "Обучение",
                onSettingsClick = { expanded = !expanded },
                onFilterChange = { filter ->
                    wordViewModel.changeMode(filter.ordinal)
                    mode = filter.ordinal
                },
                expanded = expanded,
                componentSize = componentSize,
                mode = mode,
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    componentSize = coordinates.size
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (wordUiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (wordUiState.words.isNotEmpty()) {
                            Text(text = "Слово ${wordUiState.index + 1} из ${wordUiState.size}")
                            AnimatedLearningCard(
                                currentWord = wordUiState.currentWord,
                                onSwipe = { isRight ->
                                    wordViewModel.onSwipe(isRight)
                                },
                                onLearnedClicked = { word, filter ->
                                    wordViewModel.onLearnedClicked(word, filter)
                                },
                                onImportantClicked = { word, filter ->
                                    wordViewModel.onImportantClicked(word, filter)
                                },
                                currentFilter = wordUiState.currentFilter
                            )
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

private const val TAG = "LearningScreen"