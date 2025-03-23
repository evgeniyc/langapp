package com.example.langapp.ui

import android.os.Parcelable
import com.example.langapp.data.Word
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class WordUiState(
    val words: @RawValue List<Word> = emptyList(),
    val size: Int = 0,
    val currentWord: @RawValue Word = Word(),
    val index: Int = 0,
    val isLoading: Boolean = false,
    val mode: Int = WordFilter.NOT_LEARNED.ordinal,
    val catId: Int = 0,
    val isRightSwipe: Boolean = false,
    val animationState: AnimationState = AnimationState.NONE,
    val currentFilter: WordFilter = WordFilter.NOT_LEARNED,
    val isIconChanged: Boolean = false
) : Parcelable

enum class AnimationState {
    NONE,
    SWIPE_LEFT,
    SWIPE_RIGHT,
    DELETE
}