package com.example.langapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.langapp.data.entities.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Int): Flow<WordEntity?>

    @Query("SELECT * FROM words WHERE catId = :categoryId")
    fun getWordsByCategoryId(categoryId: Int): Flow<List<WordEntity>>

    @Query("SELECT COUNT(*) FROM words WHERE catId = :categoryId AND is_learned = 1")
    fun getLearnedWordsCountByCategoryId(categoryId: Int): Flow<Int>

    @Query("SELECT CAST(COUNT(CASE WHEN is_learned = 1 THEN 1 END) AS REAL) / COUNT(*) FROM words WHERE catId = :categoryId")
    suspend fun getProgressForCategory(categoryId: Int): Float
}