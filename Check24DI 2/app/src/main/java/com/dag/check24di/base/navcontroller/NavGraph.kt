package com.dag.check24di.base.navcontroller


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dag.check24di.MainActivity
import com.dag.check24di.base.Check24Surface
import com.dag.check24di.base.appbar.CustomAppbar
import com.dag.check24di.data.Product
import com.dag.check24di.feature.home.HomeView
import com.dag.check24di.feature.product.ProductView
import com.dag.check24di.feature.webview.WebViewScreen
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    Check24Surface(
        appbar = {
            CustomAppbar()
        },
        bottomBar = {

        },
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.HomeScreen.route) {
                HomeView(
                    innerPadding = innerPadding,
                    navController = navController
                )
            }
            composable(NavScreen.WebView.route) {
                WebViewScreen()
            }
            composable(
                NavScreen.ProductScreen.route
                    .plus("/{product_id}"),
                arguments = listOf(navArgument("product_id") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong("product_id")?.let {
                    ProductView(
                        productId = it,
                        navController = navController
                    )
                }
            }
            composable(NavScreen.MainActivity.route) {
                MainActivity()
            }


        }
    }
}
