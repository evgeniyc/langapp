package com.example.langapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.ui.AppViewModelProvider
import com.example.langapp.ui.viewmodels.CategoryListViewModel
import com.example.langapp.ui.viewmodels.WordFilter
import com.example.langapp.ui.viewmodels.WordListViewModel
import com.example.langapp.ui.screens.CategoryListScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.WordListScreen

@Composable
fun NavGraph(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList.route,
    ) {
        composable(route = Screen.CategoryList.route) {
            val categoryListViewModel: CategoryListViewModel =
                viewModel(factory = AppViewModelProvider.Factory)
            CategoryListScreen(
                navController = navController,
                categoryListViewModel = categoryListViewModel
            )
        }
        composable(
            route = Screen.WordList.route,
            arguments = listOf(navArgument("catId") { type = NavType.IntType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getInt("catId") ?: 0
            val wordListViewModel: WordListViewModel = viewModel(factory = AppViewModelProvider.Factory)
            WordListScreen(
                catId = catId,
                wordListViewModel = wordListViewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.Learning.route,
            arguments = listOf(
                navArgument("catId") { type = NavType.IntType },
                navArgument("filter") { type = NavType.EnumType(WordFilter::class.java) }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("catId") ?: 0
            val filter = backStackEntry.arguments?.get("filter") as? WordFilter ?: WordFilter.ALL
            val wordListViewModel: WordListViewModel = viewModel(factory = AppViewModelProvider.Factory)
            LaunchedEffect(key1 = filter) {
                wordListViewModel.updateFilter(filter)
            }
            LearningScreen(
                wordListViewModel = wordListViewModel,
                categoryId = categoryId,
                navController = navController
            )
        }
    }
}

sealed class Screen(val route: String) {
    object CategoryList : Screen("category_list")
    object WordList : Screen("word_list/{catId}") {
        fun createRoute(catId: Int) = "word_list/$catId"
    }
    object Learning : Screen("learning/{catId}/{filter}") {
        fun createRoute(catId: Int, filter: WordFilter) = "learning/$catId/$filter"
    }
}