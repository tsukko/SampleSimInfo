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
fun NavigationHost() {
    modifier: Modifier = Modifier,
    val navController = rememberNavController(),
    // 使用するバナー広告
    // リストのスクロール量が変更して表示非表示が切り替わった場合に毎回loadAdを呼んだりしないように
    // Activityが作成された時に作って、以降はそれを使いまわす
    banner: AdView,
    nativeAdView: NativeAdView,
    startDestination: String = Top.route,
    NavHost(
        modifier = modifier,
        navController, startDestination = "home"

    ) {
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