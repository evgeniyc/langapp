package com.example.langapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.langapp.ui.WordFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordTopBar(
    title: String,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onFilterChange: (WordFilter) -> Unit = {},
    expanded: Boolean,
    componentSize: IntSize,
    mode: Int, // Добавлен параметр mode
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = textStyle)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = onSettingsClick,
                    offset = with(LocalDensity.current) {
                        DpOffset(
                            x = (componentSize.width - 16.dp.roundToPx()).toDp(),
                            y = 0.dp
                        )
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Рабочий список",
                                fontWeight = if (mode == WordFilter.NOT_LEARNED.ordinal) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onFilterChange(WordFilter.NOT_LEARNED)
                            onSettingsClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Только Важные",
                                fontWeight = if (mode == WordFilter.IMPORTANT.ordinal) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onFilterChange(WordFilter.IMPORTANT)
                            onSettingsClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Только Изученные",
                                fontWeight = if (mode == WordFilter.LEARNED.ordinal) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onFilterChange(WordFilter.LEARNED) // Исправлено: WordFilter.LEARNED
                            onSettingsClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Все вместе",
                                fontWeight = if (mode == WordFilter.ALL.ordinal) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onFilterChange(WordFilter.ALL)
                            onSettingsClick()
                        }
                    )
                }
            }
        },
        modifier = modifier
    )
}