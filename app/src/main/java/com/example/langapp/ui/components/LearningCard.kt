package com.example.langapp.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.langapp.data.Word
import com.example.langapp.ui.WordFilter

@Composable
fun LearningCard(
    word: Word,
    onLearnedClicked: (word: Word, filter: WordFilter) -> Unit,
    onImportantClicked: (word: Word, filter: WordFilter) -> Unit,
    currentFilter: WordFilter,
    isFront: Boolean,
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "LearningCard: called")
    Card(
        modifier = modifier.
        border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Используем elevation
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (isFront) {
                IconButton(
                    onClick = { onLearnedClicked(word, currentFilter) },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Learned",
                        tint = if (word.isLearned) Color.Green else Color.Gray
                    )
                }
                IconButton(
                    onClick = { onImportantClicked(word, currentFilter) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Important",
                        tint = if (word.isImportant) Color.Yellow else Color.Gray
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (word.transcription.isNotEmpty()) {
                        Text(
                            text = "[${word.transcription}]",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = word.translate,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        )
                }

            }
        }
    }
}

private const val TAG = "LearningCard"