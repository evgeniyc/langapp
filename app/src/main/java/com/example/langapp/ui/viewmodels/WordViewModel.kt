package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.data.entities.WordEntity
import com.example.langapp.data.Word
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.WordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _wordUiState = MutableStateFlow(WordUiState())
    val wordUiState: StateFlow<WordUiState> = _wordUiState.asStateFlow()
    private var mode: Int = savedStateHandle[FILTER] ?: WordFilter.NOT_LEARNED.ordinal
        set(value) {
            field = value
            savedStateHandle[FILTER] = value
        }
    var catId: Int = savedStateHandle[CATEGORY_ID] ?: 0
        set(value) {
            field = value
            savedStateHandle[CATEGORY_ID] = value
        }
    private val _allWords = MutableStateFlow<List<Word>>(emptyList())
    private val allWords: StateFlow<List<Word>> = _allWords.asStateFlow()
    private val _filteredWords = MutableStateFlow<List<Word>>(emptyList())
    private val filteredWords: StateFlow<List<Word>> = _filteredWords.asStateFlow()

    init {
        val restoredUiState = savedStateHandle.get<WordUiState>(WORD_UI_STATE)
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(catId).map { list ->
                list.map { it.toWord() }
            }.collectLatest { words ->
                _allWords.value = words
                if (restoredUiState != null) {
                    _wordUiState.value = restoredUiState.copy(isLoading = false)
                }
                filterWords()
            }
        }
    }

    private fun filterWords() {
        val filteredWords = when (WordFilter.values()[mode]) {
            WordFilter.ALL -> allWords.value
            WordFilter.LEARNED -> allWords.value.filter { it.isLearned }
            WordFilter.NOT_LEARNED -> allWords.value.filter { !it.isLearned }
            WordFilter.IMPORTANT -> allWords.value.filter { it.isImportant }
        }
        _filteredWords.value = filteredWords
        _wordUiState.update {
            it.copy(
                words = filteredWords,
                size = filteredWords.size,
                currentWord = filteredWords.firstOrNull() ?: Word(),
                index = if (filteredWords.isEmpty()) 0 else it.index,
                isLoading = false,
                mode = mode
            )
        }
        savedStateHandle[WORD_UI_STATE] = _wordUiState.value
    }

    fun changeMode(mode: Int) {
        this.mode = mode
        _wordUiState.update {
            it.copy(
                mode = mode
            )
        }
        filterWords()
    }

    fun onSwipe(isRight: Boolean) {
        val newIndex = if (isRight) {
            (_wordUiState.value.index + 1).coerceAtMost(_wordUiState.value.size - 1)
        } else {
            (_wordUiState.value.index - 1).coerceAtLeast(0)
        }
        _wordUiState.update {
            it.copy(
                index = newIndex,
                currentWord = it.words[newIndex]
            )
        }
    }

    fun onLearnedClicked(word: Word) {
        viewModelScope.launch {
            val updatedWord = word.copy(isLearned = !word.isLearned)
            wordRepository.updateWord(updatedWord.toWordEntity())
            updateWordInAllWords(updatedWord)
            filterWords()
        }
    }

    fun onImportantClicked(word: Word) {
        viewModelScope.launch {
            val updatedWord = word.copy(isImportant = !word.isImportant)
            wordRepository.updateWord(updatedWord.toWordEntity())
            updateWordInAllWords(updatedWord)
            filterWords()
        }
    }

    private fun updateWordInAllWords(updatedWord: Word) {
        _allWords.update { list ->
            list.map { if (it.id == updatedWord.id) updatedWord else it }
        }
    }

    private fun WordEntity.toWord(): Word {
        return Word(
            id = id,
            categoryId = catId,
            word = name,
            translate = transl,
            transcription = transcr,
            isLearned = is_learned,
            isImportant = is_important
        )
    }

    private fun Word.toWordEntity(): WordEntity {
        return WordEntity(
            id = id,
            catId = categoryId,
            name = word,
            transl = translate,
            transcr = transcription,
            is_learned = isLearned,
            is_important = isImportant
        )
    }

    fun getProgressForCategory(categoryId: Int): Flow<Float> = flow {
        Log.d(TAG, "getProgressForCategory: called, categoryId = $categoryId")
        val progress = withContext(Dispatchers.IO) {
            wordRepository.getProgressForCategory(categoryId)
        }
        emit(progress)
    }

    companion object {
        const val FILTER = "filter"
        const val CATEGORY_ID = "category_id"
        const val WORD_UI_STATE = "word_ui_state"
        private const val TAG = "WordViewModel"
    }
}