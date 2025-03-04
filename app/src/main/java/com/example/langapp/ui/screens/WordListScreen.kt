package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Word
import com.example.langapp.navigation.Screen
import com.example.langapp.ui.viewmodels.WordFilter
import com.example.langapp.ui.viewmodels.WordListViewModel

@Composable
fun WordListScreen(
    catId: Int,
    wordListViewModel: WordListViewModel,
    navController: NavController
) {
    val words by wordListViewModel.wordListUiState.collectAsState()
    val currentFilter = words.currentFilter
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = catId) {
        wordListViewModel.getWordsByCategoryId(catId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 92.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Список слов",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Настройки"
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Рабочий список") },
                        onClick = {
                            wordListViewModel.updateFilter(WordFilter.NOT_LEARNED)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Только Важные") },
                        onClick = {
                            wordListViewModel.updateFilter(WordFilter.IMPORTANT)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Только Изученные") },
                        onClick = {
                            wordListViewModel.updateFilter(WordFilter.LEARNED)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Все вместе") },
                        onClick = {
                            wordListViewModel.updateFilter(WordFilter.ALL)
                            expanded = false
                        }
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(words.wordList) { word ->
                WordItem(word = word)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Button(
            onClick = {
                navController.navigate(Screen.Learning.createRoute(catId, currentFilter))
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Начать",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WordItem(word: Word) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = word.name, style = MaterialTheme.typography.titleMedium)
                Text(text = word.transcr, style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = word.transl, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
