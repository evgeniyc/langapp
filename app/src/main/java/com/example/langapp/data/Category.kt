package com.example.langapp.data

data class Category(
    val id: Int,
    val name: String,
    val transl: String,
    val transcr: String,
    val progress: Float = 0f,
    val timeSpentMillis: Long = 0
)