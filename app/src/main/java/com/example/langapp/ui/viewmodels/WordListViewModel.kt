package com.example.langapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WordListViewModel(private val wordRepository: WordRepository) : ViewModel() {
    private val _wordListUiState = MutableStateFlow(WordListUiState())
    val wordListUiState: StateFlow<WordListUiState> = _wordListUiState.asStateFlow()

    private fun updateLearnedWordsCount(wordList: List<Word>) {
        val learnedCount = wordList.count { it.is_learned }
        _wordListUiState.update {
            it.copy(learnedWordsCount = learnedCount)
        }
    }

    fun getWordsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(categoryId).collect { wordList ->
                _wordListUiState.update {
                    it.copy(wordList = wordList)
                }
                updateLearnedWordsCount(wordList)
            }
        }
    }
}