package com.example.langapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.langapp.navigation.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langapp.data.entities.Category

@Composable
fun CategoryCard(
    category: Category,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                navController.navigate(Screen.WordList.createRoute(category.id))
            }
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Используем elevation
        shape = RoundedCornerShape(8.dp),
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
    }
}