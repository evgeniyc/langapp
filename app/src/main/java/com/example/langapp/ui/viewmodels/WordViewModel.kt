package com.example.langapp.ui.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.Word
import com.example.langapp.data.entities.CategoryTimeEntity
import com.example.langapp.data.entities.WordEntity
import com.example.langapp.data.repositories.CategoryTimeRepository
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.ui.AnimationState
import com.example.langapp.ui.WordFilter
import com.example.langapp.ui.WordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle,
    private val categoryTimeRepository: CategoryTimeRepository // Добавили CategoryTimeRepository
) : ViewModel() {
    private val _wordUiState = MutableStateFlow(WordUiState())
    val wordUiState: StateFlow<WordUiState> = _wordUiState.asStateFlow()
    private var mode: Int = savedStateHandle[FILTER] ?: WordFilter.NOT_LEARNED.ordinal
        set(value) {
            field = value
            savedStateHandle[FILTER] = value
        }
    private val category: StateFlow<Int> = savedStateHandle.getStateFlow(CATEGORY_ID, 0)
    private val _allWords = MutableStateFlow<List<Word>>(emptyList())
    private val allWords: StateFlow<List<Word>> = _allWords.asStateFlow()

    //region Timer
    private var timer: CountDownTimer? = null
    private var currentTimeSpentMillis: Long = 0

    private fun initializeCategoryTime() {
        viewModelScope.launch {
            val categoryTime =
                categoryTimeRepository.getCategoryTimeById(category.value).firstOrNull()
            if (categoryTime != null) {
                currentTimeSpentMillis = categoryTime.timeSpentMillis
            } else {
                // Если запись не найдена, создаем новую
                categoryTimeRepository.insertCategoryTime(
                    CategoryTimeEntity(
                        categoryId = category.value
                    )
                )
            }
        }
    }

    fun startTimer() {
        Log.d("WordViewModel", "startTimer")
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) { // Тик каждую секунду
            override fun onTick(millisUntilFinished: Long) {
                currentTimeSpentMillis += 1000 // Увеличиваем на 1 секунду (1000 миллисекунд)
                Log.d("WordViewModel", "onTick: currentTimeSpentMillis = $currentTimeSpentMillis")
            }

            override fun onFinish() {
                // Таймер закончил работу (не должно произойти, так как Long.MAX_VALUE)
                Log.d("WordViewModel", "onFinish")
            }
        }.start()
    }

    fun stopTimer() {
        Log.d("WordViewModel", "stopTimer")
        timer?.cancel()
        timer = null
        updateCategoryTime()
    }

    private fun updateCategoryTime() {
        viewModelScope.launch {
            val categoryTime = CategoryTimeEntity(
                categoryId = category.value,
                timeSpentMillis = currentTimeSpentMillis
            )
            Log.d("WordViewModel", "updateCategoryTime: categoryTime = $categoryTime")
            categoryTimeRepository.updateCategoryTime(categoryTime)
        }
    }
    //endregion

    private fun loadWords() {
        _wordUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val words = wordRepository.getWordsByCategoryId(category.value).map { list ->
                list.map { it.toWord() }
            }.first()
            _allWords.value = words
            delay(500)
            filterWords()
            _wordUiState.update { it.copy(isLoading = false) }
        }
    }

    private fun filterWords() {
        val oldIndex = wordUiState.value.index
        val newWords = when (mode) {
            WordFilter.NOT_LEARNED.ordinal -> allWords.value.filter { !it.isLearned }
            WordFilter.LEARNED.ordinal -> allWords.value.filter { it.isLearned }
            WordFilter.IMPORTANT.ordinal -> allWords.value.filter { it.isImportant }
            else -> allWords.value
        }
        val newSize = newWords.size
        val newIndex = when {
            newWords.isEmpty() -> 0
            oldIndex >= newWords.size -> newWords.size - 1
            else -> oldIndex
        }
        _wordUiState.update {
            it.copy(
                words = newWords,
                size = newSize,
                currentWord = newWords.getOrNull(newIndex) ?: Word(),
                index = newIndex,
                mode = mode
            )
        }
    }

    fun changeMode(mode: Int) {
        this.mode = mode
        filterWords()
    }

    fun onSwipe(isRight: Boolean) {
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
        _wordUiState.update {
            it.copy(
                index = newIndex,
                currentWord = it.words[newIndex],
                isRightSwipe = isRight,
                animationState = if (isRight) AnimationState.SWIPE_RIGHT else AnimationState.SWIPE_LEFT
            )
        }
    }

    fun onLearnedClicked(word: Word) {
        val oldIndex = wordUiState.value.index
        val updatedWord = word.copy(isLearned = !word.isLearned)
        viewModelScope.launch {
            wordRepository.updateWord(updatedWord.toWordEntity())
        }
        loadWords()
        val newIndex = when {
            wordUiState.value.words.isEmpty() -> 0
            oldIndex >= wordUiState.value.size -> wordUiState.value.size - 1
            else -> oldIndex
        }
        _wordUiState.update { currentState ->
            currentState.copy(
                currentWord = wordUiState.value.words.getOrNull(newIndex) ?: Word(),
                index = newIndex,
            )
        }
    }

    fun onImportantClicked(word: Word) {
        val oldIndex = wordUiState.value.index
        val updatedWord = word.copy(isImportant = !word.isImportant)
        viewModelScope.launch {
            wordRepository.updateWord(updatedWord.toWordEntity())
        }
        loadWords()
        val newIndex = when {
            wordUiState.value.words.isEmpty() -> 0
            oldIndex >= wordUiState.value.size -> wordUiState.value.size - 1
            else -> oldIndex
        }
        _wordUiState.update { currentState ->
            currentState.copy(
                currentWord = wordUiState.value.words.getOrNull(newIndex) ?: Word(),
                index = newIndex,
            )
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
        if (category.value != catId) {
            savedStateHandle[CATEGORY_ID] = catId
            initializeCategoryTime()
            loadWords()
        }
    }

    fun getCategoryId(): Int {
        return category.value
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    companion object {
        const val FILTER = "filter"
        const val CATEGORY_ID = "category"
        //private const val TAG = "WordViewModel"
    }
}

