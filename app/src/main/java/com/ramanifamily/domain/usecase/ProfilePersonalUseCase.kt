package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.ProfilePersonalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfilePersonalRepository
import retrofit2.Response

open class ProfilePersonalUseCase(private val repository: ProfilePersonalRepository) {
    suspend fun execute(request: ProfilePersonalRequest): Response<ProfileResponse> {
        return repository.profilePersonal(request)
    }
}