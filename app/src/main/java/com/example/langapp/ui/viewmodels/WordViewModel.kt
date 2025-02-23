package com.example.langapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    fun loadWordsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            wordRepository.getWordsByCategoryId(categoryId).collect {
                _words.value = it
            }
        }
    }
    fun loadAllWords() {
        viewModelScope.launch {
            wordRepository.getAllWords().collect {
                _words.value = it
            }
        }
    }

    fun getWordById(id: Int): Flow<Word> {
        return wordRepository.getWordById(id)
    }

    fun insertWord(word: Word) {
        viewModelScope.launch {
            wordRepository.insertWord(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }
}