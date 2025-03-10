package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.ui.WordUiState
import com.example.langapp.ui.WordFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "WordViewModel"
        const val CATEGORY_ID = "catId"
    }

    private val catId: Int = savedStateHandle[CATEGORY_ID] ?: 0

    private val _wordUiState = MutableStateFlow(WordUiState())
    val wordUiState: StateFlow<WordUiState> = _wordUiState.asStateFlow()

    init {
        Log.d(TAG, "init: start, catId = $catId")
        if (catId != 0) {
            getWords(catId)
        } else {
            Log.e(TAG, "init: catId is 0, this is not expected")
        }
        Log.d(TAG, "init: end")
    }

    fun updateFilter(filter: WordFilter) {
        Log.d(TAG, "updateFilter: start, filter = $filter")
        _wordUiState.update {
            it.copy(currentFilter = filter)
        }
        if (catId != 0) {
            getWords(catId)
        } else {
            Log.e(TAG, "updateFilter: catId is 0, this is not expected")
        }
        Log.d(TAG, "updateFilter: end")
    }

    private fun getWords(catId: Int) {
        Log.d(TAG, "getWords: start, catId = $catId")
        viewModelScope.launch {
            _wordUiState.update { it.copy(isLoading = true) } // Загрузка началась
            wordRepository.getWordsByCategoryId(catId).collect { words ->
                Log.d(TAG, "getWords: collect, words = $words")
                // 1. Применяем фильтр
                val filteredWords = when (_wordUiState.value.currentFilter) {
                    WordFilter.ALL -> words
                    WordFilter.LEARNED -> words.filter { it.is_learned }
                    WordFilter.NOT_LEARNED -> words.filter { !it.is_learned }
                    WordFilter.IMPORTANT -> words.filter { it.is_important }
                }
                _wordUiState.update {
                    it.copy(
                        wordList = filteredWords,
                        isLoading = false
                    )
                }
                Log.d(TAG, "getWords: collect, filteredWords = $filteredWords")
            }
        }
        Log.d(TAG, "getWords: end")
    }

    fun updateWord(word: Word) {
        Log.d(TAG, "updateWord: start, word = $word")
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
        Log.d(TAG, "updateWord: end")
    }

    fun getLearnedWordsCountByCatId(catId: Int): Flow<Int> {
        Log.d(TAG, "getLearnedWordsCountByCatId: start, catId = $catId")
        val result = wordRepository.getLearnedWordsCountByCategoryId(catId)
        Log.d(TAG, "getLearnedWordsCountByCatId: end")
        return result
    }

    fun hasWordsInCategory(categoryId: Int): Flow<Boolean> {
        Log.d(TAG, "hasWordsInCategory: start, categoryId = $categoryId")
        val result = wordRepository.getWordsByCategoryId(categoryId).map { words ->
            Log.d(TAG, "hasWordsInCategory: map, words = $words")
            words.isNotEmpty()
        }
        Log.d(TAG, "hasWordsInCategory: end")
        return result
    }
    suspend fun getProgressForCategory(categoryId: Int): Float {
        Log.d(TAG, "getProgressForCategory: start, categoryId = $categoryId")
        return withContext(viewModelScope.coroutineContext) {
            wordRepository.getProgressForCategory(categoryId)
        }
        Log.d(TAG, "getProgressForCategory: end")
    }
}