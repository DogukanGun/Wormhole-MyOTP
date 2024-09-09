package com.dag.check24di.feature.home

import com.dag.check24di.data.Header
import com.dag.check24di.data.Product
import com.dag.check24di.data.ProductEntity

data class HomeScreenState(
    val isLoading: Boolean = false,
    val filters: List<String> = emptyList(),
    val header: Header = Header("", ""),
    val products: List<Product> = emptyList(),
    val favoriteProducts: List<ProductEntity> = emptyList(),
    val error: Boolean = false
)