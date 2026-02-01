package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.usecase.ProfileBusinessUseCase
import com.ramanifamily.domain.usecase.ProfileMaritalUseCase
import com.ramanifamily.domain.usecase.ProfilePersonalUseCase

class ProfileViewModelFactory(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val profilePersonalUseCase: ProfilePersonalUseCase,
    private val profileBusinessUseCase: ProfileBusinessUseCase,
    private val profileMaritalUseCase: ProfileMaritalUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                userDataStoreRepository,
                profilePersonalUseCase,
                profileBusinessUseCase,
                profileMaritalUseCase,
                networkChecker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

