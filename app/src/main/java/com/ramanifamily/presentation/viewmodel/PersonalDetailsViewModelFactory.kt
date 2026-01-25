package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.usecase.GetDistrictsUseCase
import com.ramanifamily.domain.usecase.GetSubDistrictsUseCase

class PersonalDetailsViewModelFactory(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val getDistrictsUseCase: GetDistrictsUseCase,
    private val getSubDistrictsUseCase: GetSubDistrictsUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalDetailsViewModel::class.java)) {
            return PersonalDetailsViewModel(
                userDataStoreRepository,
                getDistrictsUseCase,
                getSubDistrictsUseCase,
                networkChecker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
