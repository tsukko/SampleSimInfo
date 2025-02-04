package jp.co.integrityworks.mysiminfo

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.co.integrityworks.mysiminfo.ui.home.DetailScreen
import jp.co.integrityworks.mysiminfo.ui.home.HomeScreen
import jp.co.integrityworks.mysiminfo.ui.home.HomeViewModel

@Composable
fun NavigationSample() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
//        composable("main") {
//            HomeScreen(navController)
//        }
        composable(
            "detail/{message}",
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(
                navController,
                backStackEntry.arguments?.getString("message") ?: "No Message"
            )
        }
        composable("home") {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(navController, viewModel)
        }
    }
}