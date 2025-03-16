package com.example.langapp.data

data class Category(
    val id: Int = 1, // Добавили значение по умолчанию 1
    val name: String,
    val transl: String,
    val transcr: String
)