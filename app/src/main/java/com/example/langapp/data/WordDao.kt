package com.example.langapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Int): Flow<Word>

    @Query("SELECT * FROM words WHERE category_id = :categoryId")
    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>>
}