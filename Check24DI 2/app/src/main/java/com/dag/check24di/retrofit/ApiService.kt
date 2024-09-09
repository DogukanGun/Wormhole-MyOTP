package com.dag.check24di.retrofit

import com.dag.check24di.data.ProductsResponse
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {

    @GET("products-test.json")
    suspend fun getProducts(): Response<ProductsResponse>
}