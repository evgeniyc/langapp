package com.example.langapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    leftText: String = "Назад",
    rightText: String = "Вперед",
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            onClick = onLeftClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Темный синий фон
                contentColor = MaterialTheme.colorScheme.onPrimary // Светлый текст
            )
        ) {
            Text(
                text = leftText,
                color = MaterialTheme.colorScheme.onPrimary // Светлый текст
            )
        }
        Button(
            onClick = onRightClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Темный синий фон
                contentColor = MaterialTheme.colorScheme.onPrimary // Светлый текст
            )
        ) {
            Text(
                text = rightText,
                color = MaterialTheme.colorScheme.onPrimary // Светлый текст
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    NavBar(onLeftClick = {}, onRightClick = {})
}