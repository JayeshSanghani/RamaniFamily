package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.MemberListRequest
import com.ramanifamily.data.entity.MemberListResponse
import com.ramanifamily.domain.repository.MemberListRepository
import retrofit2.Response

class MemberListRepositoryImpl(private val apiService: ApiService) : MemberListRepository {
    override suspend fun memberList(request: MemberListRequest): Response<MemberListResponse> {
        return apiService.memberList(request)
    }
}
