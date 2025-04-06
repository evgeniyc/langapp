package com.example.langapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.langapp.data.Word

@Composable
fun LearningCard(
    word: Word,
    onLearned: (word: Word) -> Unit,
    onImportantClicked: (word: Word) -> Unit,
    frontCardAlpha: Float,
    backCardAlpha: Float,

    ) {
    Box(
        modifier = Modifier.height(200.dp),


    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {}
        Box(
            modifier = Modifier
                //.fillMaxSize()
                .graphicsLayer {
                    alpha = backCardAlpha
                },
        ) {
            //  Текст обратной стороны
            Text(
                text = word.translate,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp)
                    .graphicsLayer {
                        rotationY = 180f //Начальное значение перевернуто
                    },

                )
        }

        //  Лицевая сторона с иконками и текстом
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .graphicsLayer {
                    alpha = frontCardAlpha
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                IconButton(
                    onClick = { onLearned(word) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Learned",
                        tint = if (word.isLearned) Color.Green else Color.Gray
                    )
                }
                IconButton(
                    onClick = { onImportantClicked(word) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Important",
                        tint = if (word.isImportant) Color.Yellow else Color.Gray
                    )
                }
            }
            Column(
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = word.word,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = word.transcription,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }

}


