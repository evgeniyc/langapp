package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.compose.animation.core.copy
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _wordUiState = MutableStateFlow(WordUiState())
    val wordUiState: StateFlow<WordUiState> = _wordUiState.asStateFlow()
    var mode: Int = savedStateHandle.get<Int>(FILTER) ?: WordFilter.NOT_LEARNED.ordinal
    var catId: Int = savedStateHandle.get<Int>(CATEGORY_ID) ?: 0
    private var allWords: List<Word> = emptyList()
    private lateinit var allWordsFlow: Flow<List<Word>>

    init {
        val restoredUiState = savedStateHandle.get<WordUiState>(WORD_UI_STATE)
        if (restoredUiState != null) {
            _wordUiState.value = restoredUiState
            allWords = savedStateHandle.get<List<Word>>(ALL_WORDS) ?: emptyList()
        } else {
            getAllWords(catId)
        }
        viewModelScope.launch {
            allWordsFlow.collectLatest {
                allWords = it
                filterWords()
            }
        }
    }

    private fun getAllWords(catId: Int) {
        viewModelScope.launch {
            _wordUiState.update { it.copy(isLoading = true) }
            delay(2000)
            allWordsFlow = wordRepository.getWordsByCategoryId(catId).map { list ->
                list.map { it.toWord() }
            }
            Log.d("WordViewModel", "getAllWords: catId = $catId")
        }
    }

    private fun filterWords() {
        val filteredWords = when (WordFilter.values()[mode]) {
            WordFilter.ALL -> allWords
            WordFilter.LEARNED -> allWords.filter { it.isLearned }
            WordFilter.NOT_LEARNED -> allWords.filter { !it.isLearned }
            WordFilter.IMPORTANT -> allWords.filter { it.isImportant }
        }
        _wordUiState.update {
            it.copy(
                words = filteredWords,
                size = filteredWords.size,
                currentWord = filteredWords.firstOrNull(),
                index = 0,
                isLoading = false,
                mode = mode
            )
        }
        savedStateHandle[WORD_UI_STATE] = _wordUiState.value
    }

    fun changeMode(mode: Int) {
        this.mode = mode
        savedStateHandle[FILTER] = mode
        filterWords()
        _wordUiState.update {
            it.copy(
                mode = mode
            )
        }
    }

    fun onSwipe(isRight: Boolean) {
        if (isRight) {
            if (_wordUiState.value.index < _wordUiState.value.size - 1) {
                _wordUiState.update {
                    it.copy(
                        index = it.index + 1,
                        currentWord = it.words[it.index + 1]
                    )
                }
            }
        } else {
            if (_wordUiState.value.index > 0) {
                _wordUiState.update {
                    it.copy(
                        index = it.index - 1,
                        currentWord = it.words[it.index - 1]
                    )
                }
            }
        }
    }

    fun onLearnedClicked(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word.toWordEntity().copy(is_learned = !word.isLearned))
        }
    }

    fun onImportantClicked(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word.toWordEntity().copy(is_important = !word.isImportant))
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
        val progress = wordRepository.getProgressForCategory(categoryId)
        emit(progress)
    }.flowOn(Dispatchers.IO)

    companion object {
        const val FILTER = "filter"
        const val CATEGORY_ID = "category_id"
        const val WORD_UI_STATE = "word_ui_state"
        const val ALL_WORDS = "all_words"
        private const val TAG = "WordViewModel"
    }
}