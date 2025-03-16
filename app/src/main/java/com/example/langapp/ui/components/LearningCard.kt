package com.example.langapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langapp.data.entities.WordEntity

@Composable
fun LearningCard(
    currentWord: WordEntity,
    isFlipped: Boolean,
    onCardClick: () -> Unit,
    onLearnedClicked: (WordEntity) -> Unit,
    onImportantClicked: (WordEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationYState by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    Box(
        modifier = modifier
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                .clickable {
                    onCardClick()
                }
                .graphicsLayer {
                    rotationY = rotationYState
                    cameraDistance = 8f * density
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Птичка
                    IconButton(
                        onClick = {
                            onLearnedClicked(currentWord)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Изучено",
                            tint = if (currentWord.is_learned) Color.Green else Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Звездочка
                    IconButton(
                        onClick = {
                            onImportantClicked(currentWord)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = if (currentWord.is_important) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Важное",
                            tint = if (currentWord.is_important) Color.Yellow else Color.Gray
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            visible = !isFlipped,
                            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 300))
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
                            visible = isFlipped,
                            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 300))
                        ) {
                            Text(
                                text = currentWord.transl,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.graphicsLayer {
                                    rotationY = if (rotationYState > 90f) 180f else 0f
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}