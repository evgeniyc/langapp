package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.ui.AppViewModelProvider
import com.example.langapp.ui.screens.CategoryScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.WordScreen
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun NavGraph(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList.route,
    ) {
        composable(route = Screen.CategoryList.route) {
            val categoryViewModel: CategoryViewModel =
                viewModel(factory = AppViewModelProvider.Factory())
            val wordViewModel: WordViewModel = viewModel(factory = AppViewModelProvider.Factory())
            CategoryScreen(
                categoryViewModel = categoryViewModel,
                wordViewModel = wordViewModel,
                onNavigateToWordList = { catId ->
                    navController.navigate(Screen.WordList.createRoute(catId))
                }
            )
        }
        composable(
            route = Screen.WordList.route,
            arguments = listOf(
                navArgument("catId") {
                    type = NavType.IntType
                    nullable = false
                },
            )
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getInt("catId") ?: throw IllegalStateException("catId is null")
            val wordViewModel: WordViewModel = viewModel(
                factory = AppViewModelProvider.Factory(catId),
            )
            WordScreen(
                wordViewModel = wordViewModel,
                onNavigateToLearning = {
                    navController.navigate(Screen.Learning.createRoute())
                },
                onNavigateToCategoryList = {
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
        composable(
            route = Screen.Learning.route,
        ) {
            val wordViewModel: WordViewModel = viewModel(
                factory = AppViewModelProvider.Factory(0),
            )
            LearningScreen(
                wordViewModel = wordViewModel,
                onNavigateToWordList = {
                    navController.navigate(Screen.WordList.createRoute(0))
                },
                onNavigateToCategoryList = {
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object CategoryList : Screen("category_list")
    object WordList : Screen("word_list/{catId}") {
        fun createRoute(catId: Int) = "word_list/$catId"
    }

    object Learning : Screen("learning") {
        fun createRoute() = "learning"
    }
}