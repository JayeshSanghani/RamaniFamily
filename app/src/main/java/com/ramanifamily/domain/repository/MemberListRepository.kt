package com.ramanifamily.domain.repository

import com.ramanifamily.data.entity.MemberListRequest
import com.ramanifamily.data.entity.MemberListResponse
import retrofit2.Response

interface MemberListRepository {
    suspend fun memberList(request: MemberListRequest): Response<MemberListResponse>
}