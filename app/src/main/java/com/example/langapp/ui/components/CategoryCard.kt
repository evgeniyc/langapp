package com.example.langapp.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langapp.data.entities.CategoryEntity

@Composable
fun CategoryCard(
    category: CategoryEntity,
    progress: Float,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val TAG = "CategoryCard"
    Card(
        modifier = modifier
            .clickable {
                onCardClick()
            }
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = category.transl,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 0.dp)
            )

            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 0.dp)
            )

            Text(
                text = category.transcr,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            if (progress > 0f) {
                Log.d("CategoryCard", "CategoryCard: progress = $progress")
                Text(
                    text = "Progess: ${(progress * 100).toInt()}%",
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp)
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .padding(top = 0.dp),
                    color = Color(0xFF008000),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
    Log.d(TAG, "CategoryCard: end")
}