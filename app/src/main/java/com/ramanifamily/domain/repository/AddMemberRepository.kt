package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import retrofit2.Response

interface AddMemberRepository {
    suspend fun addMember(request: AddMemberRequest): Response<AddMemberResponse>
}