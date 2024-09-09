package com.dag.myapplication.composebase.navcontroller

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dag.myapplication.MainActivity
import com.dag.myapplication.composebase.BlockSurface
import com.dag.myapplication.composebase.appbar.CustomAppbar
import com.dag.myapplication.composebase.bottomnavigation.CustomBottomNavigation
import com.dag.myapplication.ui.features.home.ui.HomeVM
import com.dag.myapplication.ui.features.home.ui.HomeView
import com.dag.myapplication.ui.features.passkey.ui.PasskeyVM
import com.dag.myapplication.ui.features.passkey.ui.PasskeyView

import com.solana.mobilewalletadapter.clientlib.ActivityResultSender

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavGraph(
    startDestination: String = NavScreen.WelcomeScreen.route,
    isOnboard: Boolean = false,
    activityResulSender: ActivityResultSender,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showModal by remember { mutableStateOf(ModalBottomSheetValue.Hidden) }

    BlockSurface(
        appbar = {
            if (!isOnboard) {
                CustomAppbar()
            }
        },
        bottomBar = {
            if (!isOnboard) {
                CustomBottomNavigation(
                    currentRoute = currentRoute ?: "",
                    navController = navController
                ) {
                    showModal = if (showModal == ModalBottomSheetValue.Hidden)
                        ModalBottomSheetValue.Expanded
                    else ModalBottomSheetValue.Hidden
                }
            }
        },
        backgroundColor = Color.LightGray
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeVM>()
                HomeView(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(NavScreen.PasskeyScreen.route) {
                val viewModel = hiltViewModel<PasskeyVM>()
                PasskeyView(
                    viewModel = viewModel
                )
            }
            composable(NavScreen.MainActivity.route) {
                MainActivity()
            }


        }
    }
}
