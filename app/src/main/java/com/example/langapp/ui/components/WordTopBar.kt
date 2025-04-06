package com.example.langapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.example.langapp.R
import com.example.langapp.ui.WordFilter
import kotlinx.coroutines.launch
import android.util.Log


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
    Log.d("WordTopBar", "Mode: $mode")
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
                FlagIcon()
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagIcon(modifier: Modifier = Modifier) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    val customPopupPositionProvider = object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: androidx.compose.ui.unit.LayoutDirection,
            popupContentSize: IntSize
        ): IntOffset {
            // Calculate the position below the anchor
            val x = anchorBounds.left
            val y = anchorBounds.bottom
            return IntOffset(x, y)
        }
    }

    TooltipBox(
        positionProvider = customPopupPositionProvider,
        tooltip = { PlainTooltip { Text("Приложение для изучения слов Немецкого языка уровня A1") } },
        state = tooltipState
    ) {
        Image(
            painter = painterResource(id = R.drawable.flag), // Загружаем PNG
            contentDescription = "Flag Icon",
            modifier = Modifier
                .size(36.dp) // Настройте размер по необходимости
                .padding(4.dp) // Добавьте отступы, если нужно
                .clickable(onClick = { scope.launch { tooltipState.show() } }))
    }
}
