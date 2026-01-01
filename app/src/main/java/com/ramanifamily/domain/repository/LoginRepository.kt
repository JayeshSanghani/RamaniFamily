package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun loginUser(request: LoginRequest): Response<LoginResponse>
}