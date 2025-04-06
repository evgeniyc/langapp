package com.example.langapp.ui

import com.example.langapp.data.Word

data class WordUiState(
    val words: List<Word> = emptyList(), // Убираем @RawValue
    val size: Int = 0,
    val currentWord: Word = Word(), // Убираем @RawValue
    val index: Int = 0,
    val isLoading: Boolean = false,
    val mode: Int = WordFilter.NOT_LEARNED.ordinal,
    val catId: Int = 0,
    val isRightSwipe: Boolean = false,
    val animationState: AnimationState = AnimationState.NONE,
    val currentFilter: WordFilter = WordFilter.NOT_LEARNED,
    val isIconChanged: Boolean = false
)

enum class AnimationState {
    NONE,
    SWIPE_LEFT,
    SWIPE_RIGHT,
    DELETE
}