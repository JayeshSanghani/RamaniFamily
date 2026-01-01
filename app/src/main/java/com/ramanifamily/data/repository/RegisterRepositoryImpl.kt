package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import com.ramanifamily.domain.repository.RegisterRepository
import retrofit2.Response

class RegisterRepositoryImpl(private val apiService: ApiService) : RegisterRepository {
    override suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.register(request)
    }
}
