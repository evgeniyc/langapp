package com.example.langapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.langapp.ui.AppViewModelProvider
import com.example.langapp.ui.viewmodels.CategoryListViewModel
import com.example.langapp.ui.viewmodels.LearningViewModel
import com.example.langapp.ui.viewmodels.WordListViewModel
import com.example.langapp.ui.screens.CategoryListScreen
import com.example.langapp.ui.screens.LearningScreen
import com.example.langapp.ui.screens.WordListScreen
// import com.example.langapp.navigation.Screen // Удалили импорт Screen

@Composable
fun NavGraph() {
    Log.d("NavGraph", "NavGraph() started")
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList.route,
    ) {
        Log.d("NavGraph", "NavHost started")
        composable(route = Screen.CategoryList.route) {
            Log.d("NavGraph", "composable(CategoryList) started")
            val categoryListViewModel: CategoryListViewModel =
                viewModel(factory = AppViewModelProvider.Factory)
            Log.d("NavGraph", "CategoryListViewModel created")
            CategoryListScreen(
                navController = navController,
                categoryListViewModel = categoryListViewModel
            )
            Log.d("NavGraph", "CategoryListScreen called")
        }
        composable(
            route = Screen.WordList.route,
            arguments = listOf(navArgument("catId") { type = NavType.IntType })
        ) { backStackEntry ->
            Log.d("NavGraph", "composable(WordList) started")
            val catId = backStackEntry.arguments?.getInt("catId") ?: 0
            Log.d("NavGraph", "catId = $catId")
            val wordListViewModel: WordListViewModel = viewModel(factory = AppViewModelProvider.Factory)
            Log.d("NavGraph", "WordListViewModel created")
            WordListScreen(
                catId = catId,
                wordListViewModel = wordListViewModel,
                navController = navController
            )
            Log.d("NavGraph", "WordListScreen called")
        }
        composable(
            route = Screen.Learning.route,
            arguments = listOf(navArgument("catId") { type = NavType.IntType })
        ) { backStackEntry ->
            Log.d("NavGraph", "composable(Learning) started")
            val catId = backStackEntry.arguments?.getInt("catId") ?: 0
            Log.d("NavGraph", "catId = $catId")
            val wordListBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.WordList.createRoute(catId))
            }
            val wordListViewModel: WordListViewModel = viewModel(
                factory = AppViewModelProvider.Factory,
                viewModelStoreOwner = wordListBackStackEntry
            )
            Log.d("NavGraph", "WordListViewModel created")
            val learningViewModel: LearningViewModel = viewModel(factory = AppViewModelProvider.Factory)
            Log.d("NavGraph", "LearningViewModel created")
            LearningScreen(
                wordListViewModel = wordListViewModel,
                learningViewModel = learningViewModel,
                catId = catId,
                navController = navController
            )
            Log.d("NavGraph", "LearningScreen called")
        }
    }
    Log.d("NavGraph", "NavGraph() finished")
}