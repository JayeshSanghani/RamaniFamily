package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import com.ramanifamily.data.entity.MemberListRequest
import com.ramanifamily.data.entity.MemberListResponse
import com.ramanifamily.domain.repository.AddMemberRepository
import com.ramanifamily.domain.repository.MemberListRepository
import retrofit2.Response

open class MemberListUseCase(private val repository: MemberListRepository) {
    suspend fun execute(request: MemberListRequest): Response<MemberListResponse> {
        return repository.memberList(request)
    }
}