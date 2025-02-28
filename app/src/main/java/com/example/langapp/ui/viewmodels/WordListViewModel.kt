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

enum class WordFilter {
    ALL, IMPORTANT, LEARNED, NOT_LEARNED
}

data class WordListUiState(
    val wordList: List<Word> = emptyList(),
    val currentFilter: WordFilter = WordFilter.NOT_LEARNED
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
                    it.copy(wordList = filterWords(words, it.currentFilter))
                }
            }
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
    }

    fun updateFilter(filter: WordFilter) {
        _wordListUiState.update {
            it.copy(currentFilter = filter)
        }
        val currentCategoryId = 1 //TODO: get current category id
        getWordsByCategoryId(currentCategoryId)
    }

    private fun filterWords(words: List<Word>, filter: WordFilter): List<Word> {
        return when (filter) {
            WordFilter.ALL -> words // Все слова
            WordFilter.IMPORTANT -> words.filter { it.is_important } // Важные слова
            WordFilter.LEARNED -> words.filter { it.is_learned } // Изученные слова
            WordFilter.NOT_LEARNED -> words.filter { !it.is_learned } // Неизученные слова
        }
    }
}