package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.CommonIdName
import com.ramanifamily.domain.repository.RegisterRepository
import retrofit2.Response

open class GetDistrictsUseCase (private val repository: RegisterRepository) {
    suspend fun execute(stateId: Int): Response<CommonIdName> {
        return repository.getDistricts(stateId)
    }
}