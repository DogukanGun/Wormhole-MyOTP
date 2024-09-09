package com.dag.check24products.services

import com.dag.check24products.data.ProductsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(retrofit: Retrofit): ApiSource {

    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    override suspend fun getProducts(): Flow<ProductsResponse> {
        return apiService.getProducts()
    }
}