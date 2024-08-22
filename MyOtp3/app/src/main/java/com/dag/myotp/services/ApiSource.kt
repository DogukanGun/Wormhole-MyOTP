package com.dag.myotp.services


import com.dag.myotp.data.register.RegisterRequest
import com.dag.myotp.data.register.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface ApiSource {
    suspend fun register(
        registerBody: RegisterRequest,
    ): Flow<RegisterResponse>

    suspend fun update(
        registerBody: RegisterRequest,
    ): Flow<RegisterResponse>
}