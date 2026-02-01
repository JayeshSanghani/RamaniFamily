package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.ProfileResponse
import retrofit2.Response

interface ProfileBusinessRepository {
    suspend fun profileBusiness(request: ProfileBusinessRequest): Response<ProfileResponse>
}