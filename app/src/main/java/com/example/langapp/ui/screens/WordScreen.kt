package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.components.WordCard
import com.example.langapp.ui.components.WordTopBar
import com.example.langapp.ui.viewmodels.WordViewModel
import androidx.compose.ui.unit.IntSize

@Composable
fun WordScreen(
    wordViewModel: WordViewModel,
    onNavigateToLearning: () -> Unit,
    onNavigateToCategoryList: () -> Unit
) {
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }
    CommonScreen(
        topBar = {
            WordTopBar(
                title = "Слова",
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
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                            ) {
                                items(wordUiState.words) { word ->
                                    WordCard(
                                        word = word,
                                    )
                                }
                            }
                        }
                    }
                }

            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = onNavigateToCategoryList,
                onRightClick = onNavigateToLearning,
                leftText = "Категории",
                rightText = "Обучение",
            )
        }
    )
}