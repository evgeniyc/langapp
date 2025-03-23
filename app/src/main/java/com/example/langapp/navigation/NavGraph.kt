package com.example.langapp.navigation

import android.util.Log
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
fun NavGraph() {
    Log.d(TAG, "NavGraph: called")
    val navController = rememberNavController()
    val wordViewModel: WordViewModel = viewModel(factory = AppViewModelProvider.Factory)
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList.route,
    ) {
        composable(route = Screen.CategoryList.route) {
            Log.d(TAG, "NavGraph: CategoryList route")
            val categoryViewModel: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
            CategoryScreen(
                categoryViewModel = categoryViewModel,
                onNavigateToWordList = { catId ->
                    Log.d(TAG, "NavGraph: navigate to WordList, catId = $catId")
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
            Log.d(TAG, "NavGraph: WordList route")
            val catId = backStackEntry.arguments?.getInt("catId") ?: throw IllegalStateException("catId is null")
            Log.d(TAG, "NavGraph: catId = $catId")
            wordViewModel.setCategoryId(catId)
            WordScreen(
                wordViewModel = wordViewModel,
                onNavigateToLearning = {
                    Log.d(TAG, "NavGraph: navigate to Learning")
                    navController.navigate(Screen.Learning.createRoute())
                },
                onNavigateToCategoryList = {
                    Log.d(TAG, "NavGraph: navigate to CategoryList")
                    navController.navigate(Screen.CategoryList.route)
                }
            )
        }
        composable(
            route = Screen.Learning.route,
        ) {
            Log.d(TAG, "NavGraph: Learning route")
            LearningScreen(
                wordViewModel = wordViewModel,
                onNavigateToWordList = {
                    Log.d(TAG, "NavGraph: navigate to WordList from Learning")
                    navController.navigate(Screen.WordList.createRoute(wordViewModel.getCategoryId()))
                },
                onNavigateToCategoryList = {
                    Log.d(TAG, "NavGraph: navigate to CategoryList from Learning")
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

private const val TAG = "NavGraph"