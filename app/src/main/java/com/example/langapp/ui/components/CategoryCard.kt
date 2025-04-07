package com.example.langapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langapp.R
import com.example.langapp.data.Category
import java.util.concurrent.TimeUnit

@Composable
fun CategoryCard(
    category: Category,
    categoryNumber: Int,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onCardClick() }
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            // Общий Row для всего контента
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Column( // Общий Column
                modifier = Modifier.fillMaxWidth()
            ) {
                Row( // Первый Row с разнесением по краям
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                        ) {
                        Text(
                            text = "$categoryNumber.",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(end = 8.dp),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.End

                    ) {
                        Text(
                            text = category.transl,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 0.dp),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = category.transcr,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 0.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row( // Второй Row с двумя Column
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column( // Первый Column для прогресса
                        modifier = Modifier.weight(3f)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.progress_label,
                                (category.progress * 100).toInt()
                            ),
                            fontSize = 12.sp,
                        )
                        LinearProgressIndicator(
                            progress = { category.progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Color(0xFF008000),
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                    Column( // Второй Column для времени
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.time_label),
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 10.sp)
                        )
                        Text(
                            text = formatTime(category.timeSpentMillis),
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 10.sp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

fun formatTime(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    val category = Category(
        id = 1,
        name = "Животные",
        transcr = "[ˈænɪməlz]",
        transl = "Animals",
        progress = 0.5f,
        timeSpentMillis = 120000,
    )
    MaterialTheme {
        CategoryCard(
            category = category,
            categoryNumber = 1,
            onCardClick = { /* Handle card click */ }
        )
    }
}