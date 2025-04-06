package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }

    CommonScreen(
        topBar = {
            WordTopBar(
                title = "Обучение",
                onSettingsClick = { expanded = !expanded },
                onFilterChange = { filter ->
                    wordViewModel.changeMode(filter.ordinal)
                },
                expanded = expanded,
                componentSize = componentSize,
                mode = wordUiState.mode,
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
                    if (wordUiState.words.isEmpty()) {
                        Text(text = "Список с заданными параметрами пуст.")


                    } else {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (wordUiState.words.isNotEmpty()) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Слово ${wordUiState.index + 1} из ${wordUiState.size}"
                                    )
                                    Box(modifier = Modifier.wrapContentWidth()) {
                                        LinearProgressIndicator(
                                            modifier = Modifier.width(100.dp),
                                            progress = {
                                                if (wordUiState.size != 0 && wordUiState.index + 1 <= wordUiState.size) {
                                                    1f - ((wordUiState.size.toFloat() - (wordUiState.index + 1).toFloat()) / wordUiState.size.toFloat())
                                                } else if (wordUiState.size == 0) {
                                                    0f
                                                } else {
                                                    1f
                                                }
                                            },
                                            trackColor = Color.LightGray,
                                            color = Color.Green
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                AnimatedLearningCard(
                                    words = wordUiState.words,
                                    mode = wordUiState.mode,
                                    currentIndex = wordUiState.index,
                                    onSwipe = { isRight ->
                                        wordViewModel.onSwipe(isRight)
                                    },
                                    onLearnedClicked = { word ->
                                        wordViewModel.onLearnedClicked(word)
                                    },
                                    onImportantClicked = { word ->
                                        wordViewModel.onImportantClicked(word)
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

private const val TAG = "LearningScreen"