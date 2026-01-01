package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import retrofit2.Response

interface RegisterRepository {
    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse>
}