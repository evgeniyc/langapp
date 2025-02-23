package com.example.langapp.data.repositories

import com.example.langapp.data.database.WordDao
import com.example.langapp.data.entities.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    fun getAllWords(): Flow<List<Word>> = wordDao.getAllWords()

    fun getWordById(id: Int): Flow<Word> = wordDao.getWordById(id)

    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>> =
        wordDao.getWordsByCategoryId(categoryId)

    suspend fun insertWord(word: Word) = wordDao.insertWord(word)

    suspend fun updateWord(word: Word) = wordDao.updateWord(word)

    suspend fun deleteWord(word: Word) = wordDao.deleteWord(word)
}