package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.domain.repository.LoginRepository
import retrofit2.Response

open class LoginUserUseCase(private val repository: LoginRepository) {
    suspend fun execute(request: LoginRequest): Response<LoginResponse> {
        return repository.loginUser(request)
    }
}