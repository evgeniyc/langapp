package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.Category
import com.example.langapp.data.entities.CategoryEntity
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.ui.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val wordRepository: WordRepository
) : ViewModel() {
    val categoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getAllCategories().map { categoryEntities ->
            Log.d("CategoryListViewModel", "Categories loaded: ${categoryEntities.size}")
            val progressDeferreds = categoryEntities.map { categoryEntity ->
                viewModelScope.async {
                    wordRepository.getProgressForCategory(categoryEntity.id)
                }
            }
            val progresses = progressDeferreds.awaitAll()
            val categories = categoryEntities.mapIndexed { index, categoryEntity ->
                categoryEntity.toCategory(progresses[index])
            }
            CategoryUiState(categories)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CategoryUiState()
            )

    init {
        Log.d("CategoryListViewModel", "ViewModel initialized")
    }

    suspend fun getProgressForCategory(categoryId: Int): Float {
        return wordRepository.getProgressForCategory(categoryId)
    }
}

fun CategoryEntity.toCategory(progress: Float): Category {
    return Category(
        id = id,
        name = name,
        transl = transl,
        transcr = transcr,
        progress = progress
    )
}