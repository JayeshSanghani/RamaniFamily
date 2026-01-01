package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import com.ramanifamily.domain.repository.LoginRepository
import com.ramanifamily.domain.repository.RegisterRepository
import retrofit2.Response

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {
    override suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}
