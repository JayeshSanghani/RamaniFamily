package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.repository.MemberListRepository

class DashboardViewModelFactory(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val memberListRepository: MemberListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DashboardViewModel::class.java -> {
                DashboardViewModel(
                    userDataStoreRepository,
                    memberListRepository
                ) as T
            }
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}



