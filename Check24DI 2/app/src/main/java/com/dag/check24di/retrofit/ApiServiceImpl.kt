package com.dag.check24di.retrofit

import com.dag.check24di.data.ProductsResponse
import retrofit2.Response
import retrofit2.Retrofit


class ApiServiceImpl(retrofit: Retrofit): ApiSource  {

    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    override suspend fun getProducts(): Response<ProductsResponse> {
        return apiService.getProducts()
    }

}