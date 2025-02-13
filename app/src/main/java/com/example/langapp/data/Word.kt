package com.example.langapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word_de: String,
    val word_ru: String,
    val category_id: Int,
    val is_important: Boolean = false,
    val is_learned: Boolean = false
)
