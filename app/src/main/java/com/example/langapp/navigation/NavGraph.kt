package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.ui.AppViewModelProvider
import com.example.langapp.ui.CategoryListViewModel
import com.example.langapp.ui.WordListViewModel
import com.example.langapp.ui.screens.CategoryListScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.WordListScreen
import com.example.langapp.ui.theme.LangAppTheme

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    LangAppTheme {
        NavHost(
            navController = navController,
            startDestination = "categoryList",
            modifier = modifier
        ) {
            composable("categoryList") {
                val categoryListViewModel: CategoryListViewModel =
                    viewModel(factory = AppViewModelProvider.Factory)
                CategoryListScreen(
                    navController = navController,
                    categoryListViewModel = categoryListViewModel
                )
            }
            composable(
                route = "wordList/{catId}",
                arguments = listOf(navArgument("catId") { type = NavType.IntType })
            ) { backStackEntry ->
                val catId = backStackEntry.arguments?.getInt("catId") ?: 0
                val wordListViewModel: WordListViewModel =
                    viewModel(factory = AppViewModelProvider.Factory)
                WordListScreen(
                    catId = catId, // Передаем catId
                    wordListViewModel = wordListViewModel, // Передаем wordListViewModel
                    navController = navController // Передаем navController
                )
            }
            composable(
                route = "learning/{catId}",
                arguments = listOf(navArgument("catId") { type = NavType.IntType })
            ) { backStackEntry ->
                val catId = backStackEntry.arguments?.getInt("catId") ?: 0
                val wordListViewModel: WordListViewModel =
                    viewModel(factory = AppViewModelProvider.Factory)
                LearningScreen(
                    catId = catId, // Передаем catId
                    wordListViewModel = wordListViewModel, // Передаем wordListViewModel
                    navController = navController // Передаем navController
                )
            }
        }
    }
}