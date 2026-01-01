package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.domain.usecase.RegisterUserUseCase

class RegisterViewModelFactory(
    private val registerUserUseCase: RegisterUserUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(registerUserUseCase, networkChecker) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}