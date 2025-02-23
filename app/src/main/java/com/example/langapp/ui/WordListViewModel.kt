package com.example.langapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.entities.Word
import com.example.langapp.data.repositories.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WordListViewModel(private val wordRepository: WordRepository) :
    ViewModel() { // Добавили wordRepository в конструктор и сделали его свойством класса
    fun getWordsByCategoryId(categoryId: Int): StateFlow<List<Word>> =
        wordRepository.getWordsByCategoryId(categoryId) // Используем свойство wordRepository
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}