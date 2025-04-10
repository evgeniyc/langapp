package com.example.langapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToCategories: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(5000) // Задержка в 3 секунды
        onNavigateToCategories()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.simplemapp1),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(128.dp)
        )
        /*Text(
            text = "SimpleMapp company",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
*/
        Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстом и изображением

        Image(
            painter = painterResource(id = R.drawable.iconsb),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(256.dp) // Увеличиваем размер в два раза (128 * 2 = 256)
                .clip(RoundedCornerShape(16.dp)) // Скругляем углы
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp)) // Отступ между изображением и текстом

        Text(
            text = "Карточки Немецкий A1",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pacifico_regular)),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp)) // Отступ между текстом и изображением

        Image(
            painter = painterResource(id = R.drawable.flag),
            contentDescription = "Flag Icon",
            modifier = Modifier.size(64.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}