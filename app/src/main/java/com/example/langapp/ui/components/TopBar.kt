@file:JvmName("TopBarKt")

package com.example.langapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge // Добавили параметр textStyle
) {
    TopAppBar(
        title = { Text(text = title, style = textStyle) },
        modifier = modifier
    )
}