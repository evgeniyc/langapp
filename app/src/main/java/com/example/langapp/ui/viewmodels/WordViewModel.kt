package com.example.langapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.Word
import com.example.langapp.data.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = wordRepository.getAllWords().asLiveData()

    fun getWordById(id: Int): LiveData<Word> {
        return wordRepository.getWordById(id).asLiveData()
    }

    fun getWordsByCategoryId(categoryId: Int): LiveData<List<Word>> {
        return wordRepository.getWordsByCategoryId(categoryId).asLiveData()
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