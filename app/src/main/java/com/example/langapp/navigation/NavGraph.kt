package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langapp.ui.screens.CategoryScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.SplashScreen
import com.example.langapp.ui.screens.WordScreen
import com.example.langapp.ui.viewmodels.CategoryViewModel
import com.example.langapp.ui.viewmodels.WordViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val wordViewModel: WordViewModel = hiltViewModel() // Создаём модель один раз
    var currentCatId by remember { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.CategoryList.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
        composable(route = Screen.CategoryList.route) {
            val categoryViewModel: CategoryViewModel = hiltViewModel()
            CategoryScreen(
                categoryViewModel = categoryViewModel,
                onNavigateToWordList = { catId ->
                    currentCatId = catId // Сохраняем catId
                    navController.navigate(Screen.WordList.route) // Переходим на WordList
                }
            )
        }
        composable(route = Screen.WordList.route) {
            wordViewModel.setCategoryId(currentCatId) // Вызываем setCategoryId
            WordScreen(
                wordViewModel = wordViewModel,
                onNavigateToLearning = {
                    navController.navigate(Screen.Learning.route)
                },
                onNavigateToCategoryList = {
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
        composable(route = Screen.Learning.route) {
            LearningScreen(
                wordViewModel = wordViewModel,
                onNavigateToWordList = {
                    navController.navigate(Screen.WordList.route)
                },
                onNavigateToCategoryList = {
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object CategoryList : Screen("category_list")
    object WordList : Screen("word_list") // Убрали catId
    object Learning : Screen("learning")
}

private const val TAG = "NavGraph"