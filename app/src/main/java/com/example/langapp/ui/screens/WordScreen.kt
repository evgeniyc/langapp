package com.example.langapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.components.TopBar
import com.example.langapp.ui.components.CommonScreen
import com.example.langapp.ui.components.NavBar
import com.example.langapp.ui.components.WordCard
import com.example.langapp.ui.viewmodels.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordScreen(
    wordViewModel: WordViewModel,
    onNavigateToLearning: () -> Unit,
    onNavigateToCategoryList: () -> Unit
) {
    val wordUiState by wordViewModel.wordUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val options = WordFilter.values().map { it.name }
    var selectedOptionText by remember { mutableStateOf(WordFilter.NOT_LEARNED.name) }
    CommonScreen(
        topBar = { TopBar(title = "Слова") },
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
                        Spacer(modifier = Modifier.height(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            TextField(
                                value = selectedOptionText,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                options.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(text = option) },
                                        onClick = {
                                            selectedOptionText = option
                                            expanded = false
                                            wordViewModel.changeMode(
                                                WordFilter.valueOf(option).ordinal
                                            )
                                        }
                                    )
                                }
                            }
                        }
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
        },
        bottomBar = {
            NavBar(
                onLeftClick = onNavigateToLearning,
                onRightClick = onNavigateToCategoryList,
                leftText = "Обучение",
                rightText = "Категории",
            )
        }
    )
}