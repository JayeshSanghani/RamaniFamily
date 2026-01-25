package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.data.repository.UserDataStoreRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {

    val userProfile = userDataStoreRepository.getLoginResponse()

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            userDataStoreRepository.clear()
            onComplete()
        }
    }
}
