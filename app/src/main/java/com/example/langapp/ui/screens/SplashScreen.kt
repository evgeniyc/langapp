package com.example.langapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.langapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToCategories: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(3000) // Задержка в 3 секунды
        onNavigateToCategories()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.iconsc),
            contentDescription = "App Icon",
            modifier = Modifier.size(128.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}