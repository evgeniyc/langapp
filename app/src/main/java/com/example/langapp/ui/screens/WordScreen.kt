package com.example.langapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.components.WordCard
import com.example.langapp.ui.components.WordTopBar
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun WordScreen(
    wordViewModel: WordViewModel,
    navController: NavController,
    catId: Int,
) {
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    Log.d("WordScreen", "WordScreen: wordUiState.mode = ${wordUiState.mode}")
    var expanded by remember { mutableStateOf(false) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }

    CommonScreen(
        topBar = {
            WordTopBar(
                title = "Список слов",
                onSettingsClick = {
                    expanded = !expanded
                },
                modifier = Modifier.onGloballyPositioned {
                    componentSize = it.size
                },
                onFilterChange = { filter ->
                    Log.d("WordScreen", "onFilterChange: filter.ordinal = ${filter.ordinal}")
                    wordViewModel.changeMode(filter.ordinal)
                    expanded = false
                },
                expanded = expanded,
                componentSize = componentSize,
                mode = wordUiState.mode
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (wordUiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    if (wordUiState.words.isEmpty()) {
                        Text(text = "Список с этими параметрами пуст")
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .navigationBarsPadding()
                        ) {
                            items(wordUiState.words) { word ->
                                WordCard(word = word)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = {
                    navController.navigate(Screen.CategoryList.route)
                },
                onRightClick = {
                    navController.navigate(Screen.Learning.createRoute(catId, wordUiState.mode))
                },
                leftText = "Категории",
                rightText = "Начать",
                catId = catId,
                mode = wordUiState.mode
            )
        }
    )
}