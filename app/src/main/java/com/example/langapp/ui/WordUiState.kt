package com.example.langapp.ui

import com.example.langapp.data.entities.Word

enum class WordFilter {
    ALL, IMPORTANT, LEARNED, NOT_LEARNED
}

data class WordUiState(
    val wordList: List<Word> = emptyList(),
    val currentFilter: WordFilter = WordFilter.NOT_LEARNED
)