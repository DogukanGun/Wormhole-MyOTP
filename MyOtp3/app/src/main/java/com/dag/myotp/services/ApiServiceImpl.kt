package com.dag.myotp.services

import com.dag.myotp.data.register.RegisterRequest
import com.dag.myotp.data.register.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(retrofit: Retrofit): ApiSource {

    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    override suspend fun register(@Body registerBody: RegisterRequest): Flow<RegisterResponse> {
        return apiService.register(registerBody)
    }

    override suspend fun update(@Body registerBody: RegisterRequest): Flow<RegisterResponse> {
        return apiService.update(registerBody)
    }

}