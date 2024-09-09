package com.dag.myapplication.services

import com.dag.myapplication.data.register.RegisterRequest
import com.dag.myapplication.data.register.RegisterResponse
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(retrofit: Retrofit): ApiSource  {

    private val apiService:ApiService = retrofit.create(ApiService::class.java)
    override suspend fun register(@Body registerBody: RegisterRequest): Flow<RegisterResponse> {
        return apiService.register(registerBody)
    }
}