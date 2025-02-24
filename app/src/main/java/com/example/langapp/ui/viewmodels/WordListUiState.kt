package com.example.langapp.ui.viewmodels

import com.example.langapp.data.entities.Word

data class WordListUiState(
    val wordList: List<Word> = emptyList(),
    val learnedWordsCount: Int = 0
)