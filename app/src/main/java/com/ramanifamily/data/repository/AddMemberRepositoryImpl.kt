package com.ramanifamily.data.repository

import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import com.ramanifamily.domain.repository.AddMemberRepository
import retrofit2.Response

class AddMemberRepositoryImpl(private val apiService: ApiService) : AddMemberRepository {

    override suspend fun addMember(request: AddMemberRequest): Response<AddMemberResponse> {
        return apiService.addMember(request)
    }
}
