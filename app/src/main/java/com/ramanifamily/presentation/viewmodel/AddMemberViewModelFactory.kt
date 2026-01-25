package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.domain.usecase.AddMemberUseCase

class AddMemberViewModelFactory(
    private val addMemberUseCase: AddMemberUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddMemberViewModel(
                addMemberUseCase,
                networkChecker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
