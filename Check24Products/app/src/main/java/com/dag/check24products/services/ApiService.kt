package com.dag.check24products.services

import com.dag.check24products.data.ProductsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("products-test.json")
    suspend fun getProducts(): Flow<ProductsResponse>
}