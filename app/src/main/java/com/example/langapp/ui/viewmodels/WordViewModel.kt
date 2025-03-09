package com.example.langapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.ui.WordUiState
import com.example.langapp.ui.WordFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class WordViewModel(
    private val wordRepository: WordRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val CATEGORY_ID = "catId"
    }

    private val catId: Int = savedStateHandle[CATEGORY_ID] ?: 0

    private val _wordUiState = MutableStateFlow(WordUiState())
    val wordUiState: StateFlow<WordUiState> = _wordUiState.asStateFlow()

    init {
        getWords(catId)
    }

    fun updateFilter(filter: WordFilter) {
        _wordUiState.update {
            it.copy(currentFilter = filter)
        }
        getWords(catId)
    }

    private fun getWords(catId: Int) {
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(catId).collect { words ->
                // 1. Применяем фильтр
                val filteredWords = when (_wordUiState.value.currentFilter) {
                    WordFilter.ALL -> words
                    WordFilter.LEARNED -> words.filter { it.is_learned }
                    WordFilter.NOT_LEARNED -> words.filter { !it.is_learned }
                    WordFilter.IMPORTANT -> words.filter { it.is_important }
                }
                _wordUiState.update {
                    it.copy(
                        wordList = filteredWords
                    )
                }
            }
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
    }
}