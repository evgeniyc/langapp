package com.example.langapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Анимация переворота карточки.
 *
 * @param rotationY Угол поворота.
 * @param modifier Модификатор для карточки.
 */
@Composable
fun FlipAnimation(rotationY: Float, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                cameraDistance = 8f
                this.rotationY = rotationY
            }
    ) {
        content()
    }
}