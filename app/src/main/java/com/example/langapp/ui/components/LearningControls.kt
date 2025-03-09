package com.example.langapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LearningControls(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onCategoryClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onPreviousClick) {
            Text(text = "Назад")
        }
        Button(onClick = onCategoryClick) {
            Text(text = "Категории")
        }
        Button(onClick = onNextClick) {
            Text(text = "Вперед")
        }
    }
}