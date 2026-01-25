package com.ramanifamily.domain.usecase

import com.ramanifamily.data.entity.CommonIdName
import com.ramanifamily.domain.repository.RegisterRepository
import retrofit2.Response

open class GetSubDistrictsUseCase(private val repository: RegisterRepository) {
    suspend fun execute(districtId: Int): Response<CommonIdName> {
        return repository.getSubDistricts(districtId)
    }
}