package com.dag.check24products.services

import com.dag.check24products.data.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ApiSource {
    suspend fun getProducts(): Flow<ProductsResponse>
}