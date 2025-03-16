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
import com.example.langapp.ui.WordFilter
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
            CategoryScreen(
                navController = navController,
                categoryViewModel = categoryViewModel
            )
        }
        composable(
            route = Screen.WordList.route,
            arguments = listOf(
                navArgument("catId") { type = NavType.IntType },
                navArgument("mode") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getInt("catId") ?: 0
            val mode = backStackEntry.arguments?.getInt("mode") ?: WordFilter.NOT_LEARNED.ordinal
            val wordViewModel: WordViewModel = viewModel(
                factory = AppViewModelProvider.Factory(catId),
            )
            WordScreen(
                wordViewModel = wordViewModel,
                navController = navController,
                catId = catId,
                //mode = mode
            )
        }
        composable(
            route = Screen.Learning.route,
            arguments = listOf(
                navArgument("catId") { type = NavType.IntType },
                navArgument("mode") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getInt("catId") ?: 0
            val mode = backStackEntry.arguments?.getInt("mode") ?: WordFilter.NOT_LEARNED.ordinal
            val wordViewModel: WordViewModel = viewModel(
                factory = AppViewModelProvider.Factory(catId),
            )
            LearningScreen(
                wordViewModel = wordViewModel,
                catId = catId,
                navController = navController,
            )
        }
    }
}

sealed class Screen(val route: String) {
    object CategoryList : Screen("category_list")
    object WordList : Screen("word_list/{catId}/{mode}") {
        fun createRoute(catId: Int, mode: Int) = "word_list/$catId/$mode"
    }

    object Learning : Screen("learning/{catId}/{mode}") {
        fun createRoute(catId: Int, mode: Int) = "learning/$catId/$mode"
    }
}