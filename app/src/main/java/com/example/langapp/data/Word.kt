package com.example.langapp.data

data class Word(
    val id: Int = 0,
    val categoryId: Int = 0,
    val word: String = "",
    val translate: String = "",
    val transcription: String = "",
    val isLearned: Boolean = false,
    val isImportant: Boolean = false
)