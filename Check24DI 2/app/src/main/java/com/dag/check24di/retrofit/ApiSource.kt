package com.dag.check24di.retrofit

import com.dag.check24di.data.ProductsResponse
import retrofit2.Response

interface ApiSource {
    suspend fun getProducts(): Response<ProductsResponse>

}