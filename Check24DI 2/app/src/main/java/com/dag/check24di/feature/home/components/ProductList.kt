package com.dag.check24di.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dag.check24di.base.common.FooterText
import com.dag.check24di.base.navcontroller.NavScreen
import com.dag.check24di.base.navcontroller.addData
import com.dag.check24di.data.Product
import com.dag.check24di.feature.home.HomeScreenState
import java.util.Locale


@Composable
fun ProductListView(
    innerPadding: PaddingValues,
    state: HomeScreenState,
    navController: NavHostController,
    selectedTabIndex: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // Title section
        item {
            Text(
                text = state.header.headerTitle,  // Main title
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = state.header.headerDescription,  // Subtitle
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // List of products
        if (state.products.isNotEmpty()) {
            val tempProduct = when(state.filters[selectedTabIndex].lowercase(Locale.getDefault())){
                "verfügbar" -> {
                    state.products.filter { it.available }
                }
                "vorgemerkt" -> {
                    state.favoriteProducts.map { it.getProduct(state.products) }
                }
                else -> {
                    state.products
                }

            }
            items(tempProduct) { product ->
                CellView(product = product){
                    navController.navigate(NavScreen.ProductScreen.addData("/${product.id}"))
                }
            }
        } else if (state.isLoading) {
            item {
                Text(text = "Loading please wait")
            }
        } else {
            item {
                Text(text = "Product not found")
            }
        }
        item {
            Spacer(modifier = Modifier.height(4.dp))
            FooterText(navController = navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}