package com.example.langapp.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    suspend fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    fun getAllWords(): Flow<List<Word>> {
        return wordDao.getAllWords()
    }

    fun getWordById(id: Int): Flow<Word> {
        return wordDao.getWordById(id)
    }

    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>> {
        return wordDao.getWordsByCategoryId(categoryId)
    }
}