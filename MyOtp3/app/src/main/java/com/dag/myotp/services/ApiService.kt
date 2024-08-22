package com.dag.myotp.services

import com.dag.myotp.data.register.RegisterRequest
import com.dag.myotp.data.register.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: RegisterRequest,
    ): Flow<RegisterResponse>

    @PATCH("auth/register")
    suspend fun update(
        @Body registerBody: RegisterRequest,
    ): Flow<RegisterResponse>
}