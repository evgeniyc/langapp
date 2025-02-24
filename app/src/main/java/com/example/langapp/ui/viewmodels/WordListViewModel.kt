package com.example.langapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WordListUiState(
    val wordList: List<Word> = emptyList()
)

class WordListViewModel(
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _wordListUiState = MutableStateFlow(WordListUiState())
    val wordListUiState: StateFlow<WordListUiState> = _wordListUiState.asStateFlow()

    fun getWordsByCategoryId(catId: Int) {
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(catId).collect { words ->
                _wordListUiState.update {
                    it.copy(wordList = words)
                }
            }
        }
    }
}