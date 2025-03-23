package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.data.entities.WordEntity
import com.example.langapp.data.Word
import com.example.langapp.ui.AnimationState
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.WordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val _allWords = MutableStateFlow<List<Word>>(emptyList())
    private val allWords: StateFlow<List<Word>> = _allWords.asStateFlow()

    private var currentCatId: Int = 0

    fun loadWords() {
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(getCategoryId()).map { list ->
                list.map { it.toWord() }
            }.collectLatest { words ->
                _allWords.value = words
                filterWords()
            }
        }
    }

    private fun filterWords() {
        val filteredWords = when (WordFilter.entries[mode]) {
            WordFilter.ALL -> allWords.value
            WordFilter.LEARNED -> allWords.value.filter { it.isLearned }
            WordFilter.NOT_LEARNED -> allWords.value.filter { !it.isLearned }
            WordFilter.IMPORTANT -> allWords.value.filter { it.isImportant }
        }
        val newIndex = if (filteredWords.isEmpty()) {
            0
        } else {
            if (_wordUiState.value.index >= filteredWords.size) {
                0
            } else {
                _wordUiState.value.index
            }
        }
        val newCurrentWord = if (filteredWords.isNotEmpty()) {
            if (filteredWords.contains(_wordUiState.value.currentWord)) {
                _wordUiState.value.currentWord
            } else {
                filteredWords.first()
            }
        } else {
            Word()
        }
        _wordUiState.update {
            it.copy(
                words = filteredWords,
                size = filteredWords.size,
                currentWord = newCurrentWord,
                index = newIndex,
                isLoading = false,
                mode = mode,
                catId = savedStateHandle.get<Int>(CATEGORY_ID) ?: 0,
                isRightSwipe = it.isRightSwipe,
                animationState = AnimationState.NONE,
                currentFilter = WordFilter.entries[mode]
            )
        }
        savedStateHandle[WORD_UI_STATE] = _wordUiState.value
    }

    fun changeMode(mode: Int) {
        this.mode = mode
        loadWords()
    }

    fun onSwipe(isRight: Boolean) {
        Log.d(TAG, "onSwipe: called, isRight = $isRight")
        val newIndex = if (isRight) {
            // Свайп вправо (уменьшаем индекс)
            if (_wordUiState.value.index == 0) {
                // Если карточка первая, переходим на последнюю
                _wordUiState.value.size - 1
            } else {
                // Иначе уменьшаем индекс на 1
                _wordUiState.value.index - 1
            }
        } else {
            // Свайп влево (увеличиваем индекс)
            if (_wordUiState.value.index == _wordUiState.value.size - 1) {
                // Если карточка последняя, переходим на первую
                0
            } else {
                // Иначе увеличиваем индекс на 1
                _wordUiState.value.index + 1
            }
        }
        Log.d(TAG, "onSwipe: newIndex = $newIndex")
        _wordUiState.update {
            Log.d(TAG, "onSwipe: currentWord = ${it.words.getOrElse(newIndex) { Word() }}")
            it.copy(
                index = newIndex,
                currentWord = it.words.getOrElse(newIndex) { Word() },
                isRightSwipe = isRight,
                animationState = if (isRight) AnimationState.SWIPE_RIGHT else AnimationState.SWIPE_LEFT
            )
        }
    }

    fun onLearnedClicked(word: Word, currentFilter: WordFilter) {
        viewModelScope.launch {
            val updatedWord = word.copy(isLearned = !word.isLearned)
            wordRepository.updateWord(updatedWord.toWordEntity())
            updateWordInAllWords(updatedWord)
            _wordUiState.update {
                it.copy(
                    isIconChanged = true
                )
            }
            when (currentFilter) {
                WordFilter.NOT_LEARNED -> {
                    if (!updatedWord.isLearned) {
                        filterWords()
                    } else {
                        removeWord(updatedWord)
                    }
                }

                WordFilter.LEARNED -> {
                    if (updatedWord.isLearned) {
                        filterWords()
                    } else {
                        removeWord(updatedWord)
                    }
                }

                WordFilter.IMPORTANT -> {
                    filterWords()
                }

                WordFilter.ALL -> {
                    filterWords()
                }
            }
        }
    }

    private fun removeWord(updatedWord: Word) {
        val newIndex = if (_wordUiState.value.index >= _wordUiState.value.words.size - 1) {
            0
        } else {
            _wordUiState.value.index
        }
        _wordUiState.update {
            it.copy(
                words = it.words.filter { it.id != updatedWord.id },
                size = it.words.size - 1,
                currentWord = it.words.getOrElse(newIndex) { Word() },
                index = newIndex,
                animationState = AnimationState.DELETE
            )
        }
    }

    fun onImportantClicked(word: Word, currentFilter: WordFilter) {
        viewModelScope.launch {
            val updatedWord = word.copy(isImportant = !word.isImportant)
            wordRepository.updateWord(updatedWord.toWordEntity())
            updateWordInAllWords(updatedWord)
            _wordUiState.update {
                it.copy(
                    isIconChanged = true
                )
            }
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

    fun setCategoryId(catId: Int) {
        if (currentCatId != catId) {
            currentCatId = catId
            loadWords()
        }
        savedStateHandle[CATEGORY_ID] = catId
    }

    fun getCategoryId(): Int {
        return savedStateHandle.get<Int>(CATEGORY_ID) ?: 0
    }

    companion object {
        const val FILTER = "filter"
        const val CATEGORY_ID = "category_id"
        const val WORD_UI_STATE = "word_ui_state"
        private const val TAG = "WordViewModel"
    }
}