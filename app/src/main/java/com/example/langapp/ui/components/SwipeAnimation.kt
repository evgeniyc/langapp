package com.example.langapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.langapp.data.Word
import com.example.langapp.ui.AnimationState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Анимация свайпов карточки.
 *
 * @param currentWord Текущее слово для отображения.
 * @param onSwipe Обратный вызов, вызываемый при свайпе карточки.
 * @param isVisible Флаг, указывающий, видна ли карточка.
 * @param isFirstAppearance Флаг, указывающий, является ли это первым появлением карточки.
 * @param modifier Модификатор для карточки.
 */
@Composable
fun SwipeAnimation(
    currentWord: Word,
    onSwipe: (isRight: Boolean) -> Unit,
    isVisible: Boolean,
    isFirstAppearance: Boolean,
    animationState: AnimationState,
    onAnimationStateChange: (AnimationState) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var draggedDistance = 0f
    val animatedOffsetX = remember { Animatable(0f) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val swipeOffset = screenWidth * 1.5f
    val swipeThreshold = screenWidth * 0.2f
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = animationState) {
        if (animationState != AnimationState.NONE && isVisible) {
            coroutineScope.launch {
                animatedOffsetX.animateTo(
                    targetValue = when (animationState) {
                        AnimationState.SWIPE_RIGHT -> swipeOffset.value
                        AnimationState.SWIPE_LEFT -> -swipeOffset.value
                        else -> 0f
                    },
                    animationSpec = tween(durationMillis = 500)
                )
                onSwipe(animationState == AnimationState.SWIPE_RIGHT)
                animatedOffsetX.snapTo(
                    when (animationState) {
                        AnimationState.SWIPE_RIGHT -> -swipeOffset.value
                        AnimationState.SWIPE_LEFT -> swipeOffset.value
                        else -> 0f
                    }
                )
                animatedOffsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }
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
    Box(
        modifier = modifier
            .offset { IntOffset((animatedOffsetX.value).toInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        draggedDistance += dragAmount
                        animatedOffsetX.snapTo(draggedDistance)
                    }

                    when {
                        draggedDistance.absoluteValue > swipeThreshold.value -> {
                            onAnimationStateChange(
                                if (draggedDistance > 0) {
                                    AnimationState.SWIPE_RIGHT
                                } else {
                                    AnimationState.SWIPE_LEFT
                                }
                            )
                            draggedDistance = 0f
                        }

                        draggedDistance.absoluteValue < swipeThreshold.value && animationState != AnimationState.NONE -> {
                            coroutineScope.launch {
                                draggedDistance = 0f
                                animatedOffsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                            onAnimationStateChange(AnimationState.NONE)
                        }
                    }
                }
            }
    ) {
        content()
    }
}