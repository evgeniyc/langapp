package com.example.langapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.langapp.data.entities.WordEntity

@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    word: WordEntity,
    ) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Используем elevation
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = word.name, style = MaterialTheme.typography.titleMedium)
                Text(text = word.transcr, style = MaterialTheme.typography.bodySmall)

            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(text = word.transl, style = MaterialTheme.typography.bodyMedium)

        }
    }
}