package com.example.langapp.data.repositories

import android.util.Log
import com.example.langapp.data.dao.WordDao
import com.example.langapp.data.entities.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordRepository @Inject constructor(private val wordDao: WordDao) {
    companion object {
        private const val TAG = "WordRepository"
    }
    fun getAllWords(): Flow<List<Word>> {
        Log.d(TAG, "getAllWords: start")
        val result = wordDao.getAllWords().flowOn(Dispatchers.IO)
        Log.d(TAG, "getAllWords: end")
        return result
    }

    fun getWordById(id: Int): Flow<Word?> {
        Log.d(TAG, "getWordById: start, id = $id")
        val result = wordDao.getWordById(id).flowOn(Dispatchers.IO)
        Log.d(TAG, "getWordById: end")
        return result
    }

    fun getWordsByCategoryId(categoryId: Int): Flow<List<Word>> {
        Log.d(TAG, "getWordsByCategoryId: start, categoryId = $categoryId")
        val result = wordDao.getWordsByCategoryId(categoryId).flowOn(Dispatchers.IO)
        Log.d(TAG, "getWordsByCategoryId: end")
        return result
    }

    fun getLearnedWordsCountByCategoryId(categoryId: Int): Flow<Int> {
        Log.d(TAG, "getLearnedWordsCountByCategoryId: start, categoryId = $categoryId")
        val result = wordDao.getLearnedWordsCountByCategoryId(categoryId).flowOn(Dispatchers.IO)
        Log.d(TAG, "getLearnedWordsCountByCategoryId: end")
        return result
    }

    suspend fun insertWord(word: Word) {
        Log.d(TAG, "insertWord: start, word = $word")
        withContext(Dispatchers.IO) {
            wordDao.insertWord(word)
        }
        Log.d(TAG, "insertWord: end")
    }

    suspend fun updateWord(word: Word) {
        Log.d(TAG, "updateWord: start, word = $word")
        withContext(Dispatchers.IO) {
            wordDao.updateWord(word)
        }
        Log.d(TAG, "updateWord: end")
    }

    suspend fun deleteWord(word: Word) {
        Log.d(TAG, "deleteWord: start, word = $word")
        withContext(Dispatchers.IO) {
            wordDao.deleteWord(word)
        }
        Log.d(TAG, "deleteWord: end")
    }
}