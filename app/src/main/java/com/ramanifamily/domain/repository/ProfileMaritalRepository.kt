package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.entity.ProfileResponse
import retrofit2.Response

interface ProfileMaritalRepository {
    suspend fun profileMarital(request: ProfileMaritalRequest): Response<ProfileResponse>
}