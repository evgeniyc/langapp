package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.ui.AppViewModelProvider
import com.example.langapp.ui.screens.CategoryScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.SplashScreen
import com.example.langapp.ui.screens.WordScreen
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val wordViewModel: WordViewModel = viewModel(factory = AppViewModelProvider.Factory)
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route, // Splash Screen как стартовый
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.CategoryList.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
        composable(route = Screen.CategoryList.route) {
            val categoryViewModel: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
            CategoryScreen(
                categoryViewModel = categoryViewModel,
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
            val catId =
                backStackEntry.arguments?.getInt("catId")
                    ?: throw IllegalStateException("catId is null")
            wordViewModel.setCategoryId(catId)
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
            LearningScreen(
                wordViewModel = wordViewModel,
                onNavigateToWordList = {
                    navController.navigate(
                        Screen.WordList.createRoute(wordViewModel.getCategoryId())
                    )
                },
                onNavigateToCategoryList = {
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash") // Добавили Splash Screen
    object CategoryList : Screen("category_list")
    object WordList : Screen("word_list/{catId}") {
        fun createRoute(catId: Int) = "word_list/$catId"
    }
    object Learning : Screen("learning") {
        fun createRoute() = "learning"
    }
}

private const val TAG = "NavGraph"