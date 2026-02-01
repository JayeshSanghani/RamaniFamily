package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.ProfilePersonalRequest
import com.ramanifamily.data.entity.ProfileResponse
import retrofit2.Response

interface ProfilePersonalRepository {
    suspend fun profilePersonal(request: ProfilePersonalRequest): Response<ProfileResponse>
}