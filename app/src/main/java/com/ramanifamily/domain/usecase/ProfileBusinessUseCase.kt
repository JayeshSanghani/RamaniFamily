package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfileBusinessRepository
import retrofit2.Response

open class ProfileBusinessUseCase(private val repository: ProfileBusinessRepository) {
    suspend fun execute(request: ProfileBusinessRequest): Response<ProfileResponse> {
        return repository.profileBusiness(request)
    }
}