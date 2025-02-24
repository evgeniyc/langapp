package com.example.langapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.WordRepository
import kotlinx.coroutines.launch

class LearningViewModel(private val wordRepository: WordRepository) : ViewModel() {

    fun updateWordIsLearned(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word.copy(is_learned = !word.is_learned))
        }
    }

    fun updateWordIsImportant(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWord(word.copy(is_important = !word.is_important))
        }
    }
}