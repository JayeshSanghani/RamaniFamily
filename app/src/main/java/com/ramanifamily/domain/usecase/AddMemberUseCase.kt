package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import com.ramanifamily.domain.repository.AddMemberRepository
import retrofit2.Response

open class AddMemberUseCase(private val repository: AddMemberRepository) {
    suspend fun execute(request: AddMemberRequest): Response<AddMemberResponse> {
        return repository.addMember(request)
    }
}