package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.data.AppDatabase
import com.example.langapp.data.CategoryRepository
import com.example.langapp.data.WordRepository
import com.example.langapp.ui.screens.CategoryListScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.WordListScreen
import com.example.langapp.ui.theme.LangAppTheme
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.CategoryViewModelFactory
import com.example.langapp.ui.viewmodels.WordViewModelFactory
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(scope: CoroutineScope, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getInstance(context, scope)
    val categoryRepository = CategoryRepository(database.categoryDao())
    val categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
    val wordRepository = WordRepository(database.wordDao())
    val wordViewModelFactory = WordViewModelFactory(wordRepository)
    LangAppTheme { // Добавили LangAppTheme
        NavHost(
            navController = navController,
            startDestination = "categoryList",
            modifier = modifier
        ) {
            composable("categoryList") {
                val categoryViewModel: CategoryViewModel =
                    viewModel(factory = categoryViewModelFactory)
                CategoryListScreen(
                    navController = navController,
                    categoryViewModel = categoryViewModel
                )
            }
            composable(
                route = "wordList/{categoryId}",
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                WordListScreen(
                    categoryId = categoryId,
                    wordViewModelFactory = wordViewModelFactory,
                    navController = navController
                )
            }
            composable(
                route = "learning/{categoryId}",
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                LearningScreen(
                    categoryId = categoryId,
                    wordViewModelFactory = wordViewModelFactory,
                    navController = navController
                )
            }
        }
    }
}