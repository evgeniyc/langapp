package com.example.langapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.Category
import com.example.langapp.data.database.WordDao
import com.example.langapp.data.entities.CategoryEntity
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.CategoryTimeRepository
import com.example.langapp.data.repositories.WordRepository
import com.example.langapp.ui.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository, // Изменили
    private val wordDao: WordDao,
    private val categoryTimeRepository: CategoryTimeRepository
) : ViewModel() {
    val categoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getAllCategories().map { categoryEntities ->
            Log.d("CategoryListViewModel", "Categories loaded: ${categoryEntities.size}")
            val progressDeferreds = categoryEntities.map { categoryEntity ->
                viewModelScope.async {
                    WordRepository.getProgressForCategory(categoryEntity.id, wordDao)
                }
            }
            val progresses = progressDeferreds.awaitAll()
            val categoriesWithTime = categoryEntities.mapIndexed { index, categoryEntity ->
                val categoryTime =
                    categoryTimeRepository.getCategoryTimeById(categoryEntity.id)
                        .firstOrNull()
                categoryEntity.toCategory(
                    progresses[index],
                    categoryTime?.timeSpentMillis ?: 0
                )
            }
            CategoryUiState(
                categories = categoriesWithTime,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CategoryUiState(isLoading = true)
        )
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category.toCategoryEntity())// Используем
        }
    }

    private fun Category.toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id = id,
            name = name,
            transcr = transcr,
            transl = transl
        )
    }
}

fun CategoryEntity.toCategory(progress: Float, timeSpentMillis: Long): Category {
    return Category(
        id = id,
        name = name,
        transl = transl,
        transcr = transcr,
        progress = progress,
        timeSpentMillis = timeSpentMillis
    )
}