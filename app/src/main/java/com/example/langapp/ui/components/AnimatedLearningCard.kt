package com.example.langapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.langapp.data.Word
import com.example.langapp.ui.AnimationState
import com.example.langapp.ui.WordFilter
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Анимированная карточка для изучения слов.
 *
 * @param currentWord Текущее слово для отображения.
 * @param onSwipe Обратный вызов, вызываемый при свайпе карточки.
 * @param onLearnedClicked Обратный вызов, вызываемый при нажатии на кнопку "Изучено".
 * @param onImportantClicked Обратный вызов, вызываемый при нажатии на кнопку "Важно".
 * @param currentFilter Текущий фильтр слов.
 * @param modifier Модификатор для карточки.
 */
@Composable
fun AnimatedLearningCard(
    currentWord: Word,
    onSwipe: (isRight: Boolean) -> Unit,
    onLearnedClicked: (word: Word, filter: WordFilter) -> Unit,
    onImportantClicked: (word: Word, filter: WordFilter) -> Unit,
    currentFilter: WordFilter,
    modifier: Modifier = Modifier
) {
    // Состояния
    var isFront by remember { mutableStateOf(true) }
    var rotation by remember { mutableStateOf(0f) }
    var isVisible by remember { mutableStateOf(true) }
    var isFirstAppearance by remember { mutableStateOf(true) }
    var animationState by remember { mutableStateOf(AnimationState.NONE) }

    // Анимируемое смещение
    val animatedOffsetX = remember { Animatable(0f) }

    // Ширина экрана для адаптивной анимации
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val swipeOffset = screenWidth * 1.5f // Значение для выталкивания за пределы экрана
    val swipeThreshold = screenWidth * 0.2f // Порог для определения свайпа

    // CoroutineScope для запуска анимаций
    val coroutineScope = rememberCoroutineScope()

    // Запуск анимации при изменении состояния анимации
    LaunchedEffect(key1 = animationState) {
        if (animationState != AnimationState.NONE && isVisible) {
            coroutineScope.launch {
                // Анимация "улетания"
                animatedOffsetX.animateTo(
                    targetValue = when (animationState) {
                        AnimationState.SWIPE_RIGHT -> swipeOffset.value
                        AnimationState.SWIPE_LEFT -> -swipeOffset.value
                        else -> 0f
                    },
                    animationSpec = tween(durationMillis = 500)
                )

                // Подготовка к "влетанию"
                isVisible = false
                rotation = 0f
                onSwipe(animationState == AnimationState.SWIPE_RIGHT)
                isFirstAppearance = false
                animatedOffsetX.snapTo(
                    when (animationState) {
                        AnimationState.SWIPE_RIGHT -> -swipeOffset.value
                        AnimationState.SWIPE_LEFT -> swipeOffset.value
                        else -> 0f
                    }
                )
                isVisible = true

                // Анимация "влетания"
                animatedOffsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }

    // Анимация "влетания" при первом появлении
    LaunchedEffect(key1 = currentWord, isVisible) {
        if (isVisible && isFirstAppearance) {
            coroutineScope.launch {
                animatedOffsetX.snapTo(swipeOffset.value)
                animatedOffsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }

    // Отображение карточки
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset { IntOffset(animatedOffsetX.value.toInt(), 0) }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8f
            }
            .clickable {
                rotation += 180f
                isFront = !isFront
                if (rotation >= 360f) {
                    rotation -= 360f
                }
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    when {
                        dragAmount > 0 && animatedOffsetX.value.absoluteValue < swipeThreshold.value -> {
                            animationState = AnimationState.NONE
                            animatedOffsetX.updateBounds(lowerBound = -swipeOffset.value, upperBound = swipeOffset.value)
                            coroutineScope.launch {
                                animatedOffsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        }

                        dragAmount < 0 && animatedOffsetX.value.absoluteValue < swipeThreshold.value -> {
                            animationState = AnimationState.NONE
                            animatedOffsetX.updateBounds(lowerBound = -swipeOffset.value, upperBound = swipeOffset.value)
                            coroutineScope.launch {
                                animatedOffsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        }

                        dragAmount > 0 -> animationState = AnimationState.SWIPE_RIGHT
                        dragAmount < 0 -> animationState = AnimationState.SWIPE_LEFT
                    }
                }
            }
    ) {
        LearningCard(
            word = currentWord,
            onLearnedClicked = onLearnedClicked,
            onImportantClicked = onImportantClicked,
            currentFilter = currentFilter,
            isFront = isFront,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    alpha = if (isFront) 1f else 0f
                    rotationY = if (isFront) 0f else 180f
                }
        )
    }
}