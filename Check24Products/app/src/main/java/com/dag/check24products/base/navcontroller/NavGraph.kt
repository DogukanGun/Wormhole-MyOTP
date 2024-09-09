package com.dag.check24products.base.navcontroller

import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dag.check24products.MainActivity
import com.dag.check24products.base.BlockSurface
import com.dag.check24products.base.appbar.CustomAppbar
import com.dag.check24products.base.bottomnavigation.CustomBottomNavigation
import com.dag.check24products.features.home.HomeVM
import com.dag.check24products.features.home.HomeView


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    isOnboard: Boolean = false,
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

            composable(NavScreen.MainActivity.route) {
                MainActivity()
            }
        }
    }
}
