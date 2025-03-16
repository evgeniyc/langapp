package com.example.langapp.ui

import com.example.langapp.data.Word // Правильный импорт

data class WordUiState(
    val isLoading: Boolean = false,
    val mode: Int = WordFilter.NOT_LEARNED.ordinal, // Текущий режим фильтрации
    val size: Int = 0, // Размер списка слов
    val index: Int = 0, // Индекс текущего слова
    val currentWord: Word? = null, // Текущее слово (изменено на Word)
    val words: List<Word> = emptyList(), // Список слов (изменено на List<Word>)
)