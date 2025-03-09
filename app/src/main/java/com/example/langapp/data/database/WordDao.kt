package com.example.langapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.langapp.data.entities.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Int): Flow<Word?>

    @Query("SELECT * FROM words WHERE catId = :categoryId")
    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>>

    @Query("SELECT COUNT(*) FROM words WHERE catId = :categoryId AND is_learned = 1")
    fun getLearnedWordsCountByCategoryId(categoryId: Int): Flow<Int>
}