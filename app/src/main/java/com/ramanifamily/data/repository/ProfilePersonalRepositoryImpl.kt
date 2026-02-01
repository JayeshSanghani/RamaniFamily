package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.ProfilePersonalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.domain.repository.ProfilePersonalRepository
import retrofit2.Response

class ProfilePersonalRepositoryImpl(private val apiService: ApiService) : ProfilePersonalRepository {
    override suspend fun profilePersonal(request: ProfilePersonalRequest): Response<ProfileResponse> {
        return apiService.profilePersonal(request)
    }
}
