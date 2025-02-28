package com.example.langapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langapp.data.entities.Word

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LearningCard(
    currentWord: Word,
    isFlipped: Boolean,
    onCardClick: () -> Unit,
    key: Int,
    onUpdateWord: (Word) -> Unit
) {
    val rotationYState = animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    AnimatedContent(
        targetState = key,
        transitionSpec = {
            if (targetState > initialState) {
                slideInHorizontally(
                    initialOffsetX = { screenWidth },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500)) with
                        slideOutHorizontally(
                            targetOffsetX = { -screenWidth },
                            animationSpec = tween(durationMillis = 500)
                        ) + fadeOut(animationSpec = tween(durationMillis = 500))
            } else {
                slideInHorizontally(
                    initialOffsetX = { -screenWidth },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500)) with
                        slideOutHorizontally(
                            targetOffsetX = { screenWidth },
                            animationSpec = tween(durationMillis = 500)
                        ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }.using(SizeTransform(clip = false))
        }
    ) {
        Box(modifier = Modifier
            .graphicsLayer {
                rotationY = rotationYState.value
                cameraDistance = 12f * density
            }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        onCardClick()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            ) {
                // Добавляем Box для иконок
                Box(modifier = Modifier.fillMaxSize()) {
                    var is_important by remember { mutableStateOf(currentWord.is_important) }
                    var is_learned by remember { mutableStateOf(currentWord.is_learned) }
                    // Звездочка
                    IconButton(
                        onClick = {
                            is_important = !is_important
                            onUpdateWord(currentWord.copy(is_important = is_important))
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (is_important) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Важное",
                            tint = if (is_important) Color.Yellow else Color.Gray
                        )
                    }
                    // Птичка
                    IconButton(
                        onClick = {
                            is_learned = !is_learned
                            onUpdateWord(currentWord.copy(is_learned = is_learned))
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Изучено",
                            tint = if (is_learned) Color.Green else Color.Gray
                        )
                    }
                    AnimatedContent(
                        targetState = isFlipped,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(durationMillis = 500)) togetherWith
                                    fadeOut(animationSpec = tween(durationMillis = 500))
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = FastOutSlowInEasing
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AnimatedVisibility(
                                    visible = !isFlipped,
                                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                                    exit = fadeOut(animationSpec = tween(durationMillis = 500))
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = currentWord.name,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = currentWord.transcr,
                                            fontSize = 18.sp,
                                            color = Color.Gray,
                                        )
                                    }
                                }
                                AnimatedVisibility(
                                    modifier = Modifier.graphicsLayer { rotationY = 180f },
                                    visible = isFlipped,
                                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                                    exit = fadeOut(animationSpec = tween(durationMillis = 500))
                                ) {
                                    Text(
                                        text = currentWord.transl,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}