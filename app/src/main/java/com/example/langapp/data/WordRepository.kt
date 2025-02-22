package com.example.langapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    fun getAllWords(): Flow<List<Word>> = flow {
        delay(1000) // Задержка перед получением данных
        emitAll(wordDao.getAllWords())
    }.flowOn(Dispatchers.IO)

    fun getWordById(id: Int): Flow<Word> = flow {
        delay(1000) // Задержка перед получением данных
        emitAll(wordDao.getWordById(id))
    }.flowOn(Dispatchers.IO)

    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>> = flow {
        delay(1000) // Задержка перед получением данных
        emitAll(wordDao.getWordsByCategoryId(categoryId))
    }.flowOn(Dispatchers.IO)
}