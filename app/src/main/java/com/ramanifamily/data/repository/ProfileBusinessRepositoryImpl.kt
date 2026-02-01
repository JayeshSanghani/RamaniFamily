package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfileBusinessRepository
import retrofit2.Response

class ProfileBusinessRepositoryImpl(private val apiService: ApiService) : ProfileBusinessRepository {
    override suspend fun profileBusiness(request: ProfileBusinessRequest): Response<ProfileResponse> {
        return apiService.profileBusiness(request)
    }
}
