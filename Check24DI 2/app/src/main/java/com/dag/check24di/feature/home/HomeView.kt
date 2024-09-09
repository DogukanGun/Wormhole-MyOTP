package com.dag.check24di.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dag.check24di.feature.home.components.ErrorCell
import com.dag.check24di.feature.home.components.ProductListView
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    innerPadding: PaddingValues,
    navController: NavHostController,
    homeVM: HomeVM = koinViewModel()
) {
    val state by homeVM.viewState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) } // Track selected tab index
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(key1 = Unit) {
        homeVM.getProducts()
        homeVM.clearCount()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        //no error - filters
        if (!state.error) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Gray,
                contentColor = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .zIndex(12.0F)
            ) {
                state.filters.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
        }
        if (state.error) {
            //api error case
            ErrorCell(innerPadding) {
                homeVM.getProducts()
            }
        } else {
            //no error
            ProductListView(innerPadding, state, navController,selectedTabIndex)
        }
        //pull refresh feature
        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                homeVM.getProducts()
                selectedTabIndex = 0
            }
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )
        LaunchedEffect(state.isLoading) {
            if (state.isLoading) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    HomeView(
        PaddingValues(),
        navController = rememberNavController()
    )
}