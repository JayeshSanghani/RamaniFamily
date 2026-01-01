package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.usecase.LoginUserUseCase

class LoginViewModelFactory(
    private val useCase: LoginUserUseCase,
    private val networkChecker: NetworkChecker,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return LoginViewModel(
                useCase,
                networkChecker,
                userDataStoreRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}