package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import com.ramanifamily.domain.repository.RegisterRepository
import retrofit2.Response

open class RegisterUserUseCase(private val repository: RegisterRepository) {
    suspend fun execute(request: RegisterRequest): Response<RegisterResponse> {
        return repository.registerUser(request)
    }
}