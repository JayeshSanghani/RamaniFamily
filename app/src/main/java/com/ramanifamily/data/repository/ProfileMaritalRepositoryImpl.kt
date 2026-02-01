package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfileMaritalRepository
import retrofit2.Response

class ProfileMaritalRepositoryImpl(private val apiService: ApiService) : ProfileMaritalRepository {
    override suspend fun profileMarital(request: ProfileMaritalRequest): Response<ProfileResponse> {
        return apiService.profileMarital(request)
    }
}
