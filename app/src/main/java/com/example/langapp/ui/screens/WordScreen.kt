package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Word
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.components.WordCard
import com.example.langapp.ui.components.Screen
import com.example.langapp.ui.components.WordListTopBar
import com.example.langapp.ui.viewmodels.WordViewModel
import androidx.compose.foundation.lazy.items
import com.example.langapp.ui.components.NavBar

@Composable
fun WordScreen(
    wordViewModel: WordViewModel,
    navController: NavController,
    catId: Int
) {
    wordViewModel.savedStateHandle[WordViewModel.CATEGORY_ID] = catId
    val words by wordViewModel.wordUiState.collectAsState()
    val currentFilter = words.currentFilter
    var expanded by remember { mutableStateOf(false) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }

    Screen(
        topBar = {
            WordListTopBar(
                title = "Список слов",
                onSettingsClick = {
                    expanded = !expanded
                },
                modifier = Modifier.onGloballyPositioned {
                    componentSize = it.size
                },
                onFilterChange = { filter ->
                    wordViewModel.updateFilter(filter)
                    expanded = false
                },
                expanded = expanded,
                componentSize = componentSize
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(words.wordList) { word ->
                    WordCard(word = word)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        bottomBar = {
            NavBar(
                onLeftClick = {
                    navController.navigate(Screen.CategoryList.route)
                },
                onRightClick = {
                    navController.navigate(Screen.Learning.createRoute(catId, currentFilter))
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                leftText = "Категории",
                rightText = "Начать"
            )
        }
    )
}