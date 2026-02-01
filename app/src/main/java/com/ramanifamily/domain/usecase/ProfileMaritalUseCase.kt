package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfileMaritalRepository
import retrofit2.Response

open class ProfileMaritalUseCase(private val repository: ProfileMaritalRepository) {
    suspend fun execute(request: ProfileMaritalRequest): Response<ProfileResponse> {
        return repository.profileMarital(request)
    }
}