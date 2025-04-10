package com.example.langapp.data.database

import android.util.Log
import com.example.langapp.constants.Categories
import com.example.langapp.data.entities.CategoryEntity
import com.example.langapp.data.entities.WordEntity
import com.example.langapp.data.repositories.CategoryRepository
import com.example.langapp.data.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val scope: CoroutineScope,
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository,
) {
    companion object {
        private const val TAG = "DatabaseInitializer"
    }

    fun initialize() {
        scope.launch {
            try {
                Log.d(TAG, "Initializing database...")
                val categoriesExist = categoryRepository.getAllCategories().firstOrNull()?.isNotEmpty() ?: false
                if (!categoriesExist) {
                    Log.d(TAG, "Database is empty, first initialization")
                    addFirstData(wordRepository, categoryRepository)
                } else {
                    Log.d(TAG, "Database exists")
                    populateDatabase(wordRepository, categoryRepository)
                }
                Log.d(TAG, "Database initialized successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing database", e)
            }
        }
    }

    private suspend fun populateDatabase(
        wordRepository: WordRepository,
        categoryRepository: CategoryRepository
    ) {
        Log.d(TAG, "Populating database...")
        addCategories(categoryRepository)
        val categoriesExist = categoryRepository.getAllCategories().firstOrNull()?.isNotEmpty() ?: false
        if (categoriesExist) {
            Log.d(TAG, "Categories exist, adding words...")
            addWords(wordRepository, categoryRepository)
        } else {
            Log.e(TAG, "No categories found in database")
        }
        Log.d(TAG, "Populating database finished.")
    }

    private suspend fun addFirstData(
        wordRepository: WordRepository,
        categoryRepository: CategoryRepository
    ) {
        Log.d(TAG, "addFirstData...")
        addFirstCategories(categoryRepository)
        val categories = categoryRepository.getAllCategories().firstOrNull() ?: emptyList()
        categories.forEach {
            addFirstWords(wordRepository, it.name, it.id)
        }
        Log.d(TAG, "addFirstData finished.")
    }

    private suspend fun addCategories(categoryRepository: CategoryRepository) {
        Log.d(TAG, "Adding categories...")
        val existingCategoriesFlow = categoryRepository.getAllCategories()
        val newCategories = Categories.categoryList
        val existingCategories: List<CategoryEntity> = existingCategoriesFlow.firstOrNull() ?: emptyList()

        Log.d(TAG, "Found ${existingCategories.size} existing categories")

        val categoriesToAdd = newCategories.filter { newCategory ->
            existingCategories.none { it.id == newCategory.id }
        }
        Log.d(TAG, "Adding ${categoriesToAdd.size} new categories")

        categoriesToAdd.forEach {
            Log.d(TAG, "Inserting category: ${it.name}")
            categoryRepository.insertCategory(it)
        }

        val categoriesToDelete = existingCategories.filter { existingCategory ->
            newCategories.none { it.id == existingCategory.id }
        }
        Log.d(TAG, "Deleting ${categoriesToDelete.size} categories")

        categoriesToDelete.forEach {
            Log.d(TAG, "Deleting category: ${it.name}")
            categoryRepository.deleteCategory(it)
        }
        Log.d(TAG, "Adding categories finished.")
    }

    private suspend fun addFirstCategories(categoryRepository: CategoryRepository) {
        Log.d(TAG, "Adding categories...")
        val newCategories = Categories.categoryList
        newCategories.forEach {
            Log.d(TAG, "Inserting category: ${it.name}")
            categoryRepository.insertCategory(it)
        }
        Log.d(TAG, "Adding categories finished.")
    }

    private suspend fun addWords(
        wordRepository: WordRepository,
        categoryRepository: CategoryRepository
    ) {
        Log.d(TAG, "Adding words...")
        val categories = categoryRepository.getAllCategories().firstOrNull() ?: emptyList()
        categories.forEach {
            addWordsForCategory(wordRepository, it.name, it.id)
        }
        Log.d(TAG, "Adding words finished.")
    }

    private suspend fun addFirstWords(
        wordRepository: WordRepository,
        categoryName: String,
        categoryId: Int
    ) {
        Log.d(TAG, "addFirstWords category: $categoryName")
        addWordsToDatabase(wordRepository, categoryName, categoryId)
    }

    private suspend fun addWordsForCategory(
        wordRepository: WordRepository,
        categoryName: String,
        categoryId: Int
    ) {
        Log.d(TAG, "addWordsForCategory category: $categoryName")
        addWordsToDatabase(wordRepository, categoryName, categoryId)
    }

    private suspend fun addWordsToDatabase(
        wordRepository: WordRepository,
        categoryName: String,
        categoryId: Int
    ) {
        val cleanedCategoryName = categoryName.replace(Regex("[^a-zA-Z]"), "")
        try {
            val className = "com.example.langapp.constants.$cleanedCategoryName"
            val clazz = Class.forName(className)
            val wordListField = clazz.getDeclaredField("wordList")
            wordListField.isAccessible = true
            val wordList = wordListField.get(null) as List<WordEntity>
            // Получаем все слова из базы данных для данной категории
            val existingWords = wordRepository.getWordsByCategoryId(categoryId).firstOrNull() ?: emptyList()

            // Удаляем из базы данных слова, которых нет в wordList
            val wordsToDelete = existingWords.filter { existingWord ->
                wordList.none { it.name.equals(existingWord.name, ignoreCase = true) }
            }
            wordsToDelete.forEach { word ->
                Log.d(TAG, "Deleting word: ${word.name} in category: $categoryName")
                wordRepository.deleteWord(word)
            }
            // Добавляем новые или обновляем существующие слова
            wordList.forEach { word ->
                val existingWord = existingWords.find { it.name.equals(word.name, ignoreCase = true) }
                if (existingWord == null) {
                    Log.d(TAG, "Inserting word: ${word.name} in category: $categoryName")
                    wordRepository.insertWord(word)
                } else {
                    if (existingWord != word) {
                        Log.d(TAG, "Updating word: ${word.name} in category: $categoryName")
                        wordRepository.updateWord(word)
                    } else {
                        Log.d(TAG, "Word ${word.name} already exists in category: $categoryName")
                    }
                }
            }
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "Class not found for category: $categoryName", e)
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, "Field 'wordList' not found in category: $categoryName", e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Illegal access to field 'wordList' in category: $categoryName", e)
        }
    }
}