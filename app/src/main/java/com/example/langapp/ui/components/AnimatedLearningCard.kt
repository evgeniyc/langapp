package com.example.langapp.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
private const val SWIPE_THRESHOLD = 200f

@Composable
fun AnimatedLearningCard(
    words: List<Word>, // Список слов для обучения
    currentIndex: Int, // Индекс текущего слова в списке
    onSwipe: (isRight: Boolean) -> Unit, // Функция, вызываемая при завершении свайпа, передает true если вправо, false если влево
    onLearnedClicked: (word: Word) -> Unit, // Функция, вызываемая при нажатии на кнопку "Выучено"
    onImportantClicked: (word: Word) -> Unit, // Функция, вызываемая при нажатии на кнопку "Важно"
    modifier: Modifier = Modifier
) {
    // Состояние, определяющее, какая сторона карточки сейчас видна (true - передняя, false - задняя)
    var isFront by remember { mutableStateOf(true) }
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
    // Создаем анимированное значение для alpha лицевого слоя
    val alphaFront by derivedStateOf {
        if (rotationAnimation <= 90f) {
            1f - rotationAnimation / 90f
        } else {
            0f
        }
    }
    // Создаем анимированное значение для alpha тыльного слоя
    val alphaBack by derivedStateOf {
        if (rotationAnimation <= 90f) {
            0f
        } else {
            (rotationAnimation - 90f) / 90f
        }
    }
    // Состояние, определяющее, находится ли карточка в анимации свайпа
    var isAnimating by remember { mutableStateOf(false) }
    // Текущее смещение карточки по оси X (горизонтальное смещение)
    var offsetX by remember { mutableFloatStateOf(0f) }
    // Целевое смещение карточки по оси X, к которому стремится анимация (общее для обеих карточек)
    var targetOffsetX by remember { mutableFloatStateOf(0f) }
    // Анимированное смещение карточки по оси X, которое изменяется в процессе анимации
    val animatedOffsetX by animateFloatAsState(
        targetValue = targetOffsetX, // Целевое значение для анимации
        animationSpec = tween(
            durationMillis = 250,
            easing = LinearEasing
        ), // Длительность анимации - 250 миллисекунд, тип - линейное изменение
        label = "MainCard Offset animation",
        finishedListener = {
            if (isAnimating) {
                onSwipe(isRightSwipe)
            }
        }
    )
    //Анимированное смещение для дополнительной карточки
    val extraAnimatedOffsetX by animateFloatAsState(
        targetValue = -targetOffsetX, // Целевое значение для анимации
        animationSpec = tween(
            durationMillis = 250,
            easing = LinearEasing
        ), // Длительность анимации - 250 миллисекунд, тип - линейное изменение
        label = "ExtraCard Offset animation",
        finishedListener = { //Конец анимации влета
            if (isAnimating) {
                targetOffsetX = 0f
                isAnimating = false
            }
        }
    )
    val configuration = LocalConfiguration.current // Получаем текущую конфигурацию экрана
    val screenWidth = configuration.screenWidthDp.dp // Получаем ширину экрана в dp
    val offScreen =
        screenWidth * 2// Определяем расстояние, на которое карточка должна вылететь за пределы экрана
    val swipeOffset = offScreen.value // Преобразуем offScreen в значение типа Float

    // Эффект, запускаемый при изменении isAnimating
    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            targetOffsetX = if (isRightSwipe) swipeOffset else -swipeOffset
        }
    }


    // Box, содержащий карточку
    Box(
        modifier = modifier
            .fillMaxWidth() // Занимает всю доступную ширину
            .padding(top = 16.dp) // Отступ сверху - 16 dp
            .offset {
                IntOffset(
                    animatedOffsetX.roundToInt(),
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
                        isRightSwipe = dragAmount > 0 // Определяем направление свайпа
                    },
                    onDragEnd = {
                        if (offsetX.absoluteValue < SWIPE_THRESHOLD) {
                            targetOffsetX = 0f
                            isAnimating = false
                        } else {
                            isAnimating = true
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
        // Box, содержащий основную карточку
        Box(modifier = Modifier.graphicsLayer {
            alpha = if (isAnimating) 0f else 1f
            translationX = animatedOffsetX
            rotationY = rotationAnimation
            cameraDistance = 8 * density
        })
        {
            LearningCard(
                // Отображаем переднюю сторону карточки
                word = currentWord, // Передаем текущее слово
                onLearnedClicked = onLearnedClicked, // Передаем обработчик нажатия на кнопку "Выучено"
                onImportantClicked = onImportantClicked, // Передаем обработчик нажатия на кнопку "Важно"
                isFront = isFront,
                frontCardAlpha = alphaFront,
                backCardAlpha = alphaBack
            )

        }
        // Box, содержащий переднюю сторону дополнительной карточки
        Box(modifier = Modifier.graphicsLayer {
            alpha = if (isAnimating) 1f else 0f
            translationX = extraAnimatedOffsetX
        }) {
            LearningCard(
                // Отображаем переднюю сторону карточки
                word = currentWord, // Передаем текущее слово
                onLearnedClicked = onLearnedClicked, // Передаем обработчик нажатия на кнопку "Выучено"
                onImportantClicked = onImportantClicked, // Передаем обработчик нажатия на кнопку "Важно"
                frontCardAlpha = 1f,
                backCardAlpha = 0f
                )

        }


    }
}