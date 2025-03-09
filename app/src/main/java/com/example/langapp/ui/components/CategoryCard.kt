package com.example.langapp.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Category
import com.example.langapp.navigation.Screen

@Composable
fun CategoryCard(
    category: Category,
    navController: NavController,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val TAG = "CategoryCard"
    Log.d(TAG, "CategoryCard: start, category = $category, progress = $progress")
    Card(
        modifier = modifier
            .clickable {
                Log.d(TAG, "CategoryCard: clickable, catId = ${category.id}")
                navController.navigate(Screen.WordList.createRoute(category.id))
            }
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = category.transl, style = MaterialTheme.typography.titleMedium)

                Text(text = category.name, style = MaterialTheme.typography.bodyMedium)

                Text(text = category.transcr, style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(10.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Log.d(TAG, "CategoryCard: end")
}