package com.example.langapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.langapp.data.Category
import com.example.langapp.data.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val allCategories: LiveData<List<Category>> = categoryRepository.getAllCategories().asLiveData()

    fun getCategoryById(id: Int): LiveData<Category> {
        return categoryRepository.getCategoryById(id).asLiveData()
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}