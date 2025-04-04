package com.example.langapp.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.langapp.data.Word
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

// Константа, определяющая порог свайпа, после которого считается, что свайп завершен
private const val SWIPE_THRESHOLD = 100f

@Composable
fun AnimatedLearningCard(
    modifier: Modifier = Modifier,
    words: List<Word>, // Список слов для обучения
    mode: Int = 0, //
    currentIndex: Int, // Индекс текущего слова в списке
    onSwipe: (isRight: Boolean) -> Unit, // Функция, вызываемая при завершении свайпа, передает true если вправо, false если влево
    onLearnedClicked: (word: Word) -> Unit, // Функция, вызываемая при нажатии на кнопку "Выучено"
    onImportantClicked: (word: Word) -> Unit, // Функция, вызываемая при нажатии на кнопку "Важно"

) {
    // Состояние, определяющее, какая сторона карточки сейчас видна (true - передняя, false - задняя)
    var isFront by remember { mutableStateOf(true) }
    // Состояние, определяющее, находится ли карточка в анимации свайпа
    var isOutAnimating by remember { mutableStateOf(false) }
    var isInAnimating by remember { mutableStateOf(false) }
    // Текущее смещение карточки по оси X (горизонтальное смещение)
    var offsetX by remember { mutableFloatStateOf(0f) }
    // Максимальное смещение карточки по оси X (горизонтальное смещение)
    val swipeOffset = LocalConfiguration.current.screenWidthDp.dp.value * 1.5f
    // Целевое смещение карточки по оси X, к которому стремится анимация (общее для обеих карточек)
    var targetOffsetX by remember { mutableFloatStateOf(0f) }
    // Текущее слово, отображаемое на карточке, обновляется при смене индекса или списка слов
    val currentWord by remember(currentIndex, words) {
        mutableStateOf(words.getOrElse(currentIndex) { Word() })
    }
    // Состояние, определяющее, был ли свайп вправо (true) или влево (false)
    var isRightSwipe by remember { mutableStateOf(false) }
    // Анимация вращения карточки
    val rotationAnimation by animateFloatAsState(
        targetValue = if (isFront) 0f else 180f, // Угол вращения зависит от isFront
        animationSpec = tween(
            durationMillis = 600, // Длительность анимации - 1000 миллисекунд
            easing = LinearEasing // Тип анимации - линейное изменение
        ), label = "Card animated rotation"
    )
    var isFadeOut by remember { mutableStateOf(false) }
    val fadeOutAnimation by animateFloatAsState(
        targetValue = if (isFadeOut) 0f else 1f, // Прозрачность
        animationSpec = tween(
            durationMillis = 600, // Длительность анимации - 600 миллисекунд
            easing = LinearEasing // Тип анимации - линейное изменение
        ), label = "Card animated fading Out",
        finishedListener = {
            if(isFadeOut){
                targetOffsetX = swipeOffset
                isOutAnimating = true
                isFadeOut = false
            }
        }
    )

    val alphaFront by remember(rotationAnimation, isFadeOut) {
        derivedStateOf {
            if (isFadeOut) {
                fadeOutAnimation
            } else {
                if (rotationAnimation <= 90f) {
                    1f - rotationAnimation / 90f
                } else {
                    0f
                }
            }
        }
    }
    // Создаем анимированное значение для alpha тыльного слоя
    val alphaBack by remember(rotationAnimation) {
        derivedStateOf {
            if (rotationAnimation <= 90f) {
                0f
            } else {
                (rotationAnimation - 90f) / 90f
            }
        }
    }


    // Анимированное смещение карточки по оси X, которое изменяется в процессе анимации
    val animatedOffsetX by animateFloatAsState(
        targetValue = targetOffsetX, // Целевое значение для анимации
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        ), // Длительность анимации - 250 миллисекунд, тип - линейное изменение
        label = "MainCard Offset animation",
        finishedListener = {
            if (isOutAnimating) {
                onSwipe(isRightSwipe)
                isFront = true
                isOutAnimating = false
                isInAnimating = true
                targetOffsetX = 0f
            } else {
                isInAnimating = false
            }
        }
    )

Box(modifier = modifier
    //.fillMaxSize()
) {
    // Box, содержащий дополнительную карточку
    Box(
        modifier = modifier
            .graphicsLayer {
                alpha = if (isInAnimating) 1f else 0f
                translationX = -animatedOffsetX

            }
            .fillMaxWidth() // Занимает всю доступную ширину
            .padding(top = 16.dp) // Отступ сверху - 16 dp
    ) {
        LearningCard(
            // Отображаем дополнительную карточку при необходимости
            word = currentWord, // Передаем текущее слово
            onLearned = onLearnedClicked,
            onImportantClicked = onImportantClicked, // Передаем обработчик нажатия на кнопку "Важно"
            frontCardAlpha = 1f,
            backCardAlpha = 0f
        )

    }
    // Box, содержащий карточку
    Box(
        modifier = modifier
            .graphicsLayer {
                alpha = if (!isInAnimating) 1f else 0f
                translationX = if (isOutAnimating) animatedOffsetX else 0f
                rotationY = rotationAnimation
                cameraDistance = 8 * density
            }
            .fillMaxWidth() // Занимает всю доступную ширину
            .padding(top = 16.dp) // Отступ сверху - 16 dp
            .offset {
                IntOffset(
                    targetOffsetX.roundToInt(),
                    0
                )
            } // Устанавливаем горизонтальное смещение карточки
            .pointerInput(Unit) { // Отслеживаем жесты
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->  // Обрабатываем горизонтальные жесты
                        change.consume() // Поглощаем событие
                        offsetX += dragAmount // Изменяем offsetX в зависимости от величины смещения
                        targetOffsetX =
                            offsetX // Обновляем targetOffsetX, чтобы анимация следовала за движением пальца

                    },
                    onDragEnd = {
                        isRightSwipe = offsetX > 0
                        if (offsetX.absoluteValue < SWIPE_THRESHOLD) {
                            targetOffsetX = 0f

                        } else {
                            targetOffsetX = if (isRightSwipe) swipeOffset else -swipeOffset
                            isOutAnimating = true


                        }
                        offsetX = 0f
                    }
                )
            }

            .clickable( // Добавляем обработчик клика
                enabled = offsetX.absoluteValue < SWIPE_THRESHOLD && (rotationAnimation == 0f || rotationAnimation == 180f) && animatedOffsetX == 0f // Клик доступен, если нет свайпа и анимации
            ) {
                (offsetX == 0f && animatedOffsetX == 0f).takeIf { it }?.also {
                    isFront = !isFront
                } // Если карточка не смещена, то переворачиваем её
            }
    ) {
        LearningCard(
            // Отображаем дополнительную карточку
            word = currentWord, // Передаем текущее слово
            onLearned = {
                isFadeOut = mode == 0
                onLearnedClicked(currentWord)
            },
            onImportantClicked = onImportantClicked, // Передаем обработчик нажатия на кнопку "Важно"
            frontCardAlpha = alphaFront,
            backCardAlpha = alphaBack
        )

    }

}

}
