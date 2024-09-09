package com.dag.check24di.data

data class ProductsResponse(
    val header: Header,
    val filters: List<String>,
    val products: List<Product>,
)

data class Header(
    val headerTitle: String,
    val headerDescription: String,
)

