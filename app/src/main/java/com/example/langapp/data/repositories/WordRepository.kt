package com.example.langapp.data.repositories

import android.util.Log
import com.example.langapp.data.database.WordDao
import com.example.langapp.data.entities.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WordRepository @Inject constructor(private val wordDao: WordDao) {
    companion object {
        private const val TAG = "WordRepository"
    }

    fun getAllWords(): Flow<List<WordEntity>> {
        Log.d(TAG, "getAllWords: called")
        return wordDao.getAllWords().flowOn(Dispatchers.IO)
    }

    fun getWordById(id: Int): Flow<WordEntity?> {
        Log.d(TAG, "getWordById: called, id = $id")
        return wordDao.getWordById(id).flowOn(Dispatchers.IO)
    }

    fun getWordsByCategoryId(categoryId: Int): Flow<List<WordEntity>> {
        Log.d(TAG, "getWordsByCategoryId: called, categoryId = $categoryId")
        return wordDao.getWordsByCategoryId(categoryId).flowOn(Dispatchers.IO)
    }

    fun getLearnedWordsCountByCategoryId(categoryId: Int): Flow<Int> {
        Log.d(TAG, "getLearnedWordsCountByCategoryId: called, categoryId = $categoryId")
        return wordDao.getLearnedWordsCountByCategoryId(categoryId).flowOn(Dispatchers.IO)
    }

    suspend fun insertWord(word: WordEntity) {
        Log.d(TAG, "insertWord: called, word = $word")
        wordDao.insertWord(word)
    }

    suspend fun updateWord(word: WordEntity) {
        Log.d(TAG, "updateWord: called, word = $word")
        wordDao.updateWord(word)
    }

    suspend fun deleteWord(word: WordEntity) {
        Log.d(TAG, "deleteWord: called, word = $word")
        wordDao.deleteWord(word)
    }

    suspend fun getProgressForCategory(categoryId: Int): Float {
        return wordDao.getProgressForCategory(categoryId)
    }
}