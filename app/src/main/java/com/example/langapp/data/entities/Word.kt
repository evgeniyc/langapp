package com.example.langapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["catId"],
        onDelete = ForeignKey.CASCADE
    )],
            indices = [Index(value = ["catId"])]
)
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "transl") val transl: String,
    @ColumnInfo(name = "transcr") val transcr: String,
    @ColumnInfo(name = "catId") val catId: Int,
    @ColumnInfo(name = "is_important") val is_important: Boolean = false,
    @ColumnInfo(name = "is_learned") val is_learned: Boolean = false
)