package com.example.langapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    leftText: String = "Назад",
    rightText: String = "Вперед",
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        //.wrapContentHeight()
        //.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        //verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onLeftClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp, // Тень в обычном состоянии
                pressedElevation = 12.dp, // Тень при нажатии
                disabledElevation = 0.dp // Тень в отключенном состоянии
            )
        ) {
            Text(
                text = leftText,
                //color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Button(
            onClick = onRightClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp, // Тень в обычном состоянии
                pressedElevation = 12.dp, // Тень при нажатии
                disabledElevation = 0.dp // Тень в отключенном состоянии
            )
        ) {
            Text(
                text = rightText,
                //color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    NavBar(onLeftClick = {}, onRightClick = {})
}