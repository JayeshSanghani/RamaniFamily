package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramanifamily.data.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.*

class SplashViewModel(
    private val repository: UserDataStoreRepository
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> =
        repository.loginResponseFlow
            .map { it.token.isNotEmpty() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}

class SplashViewModelFactory(
    private val repository: UserDataStoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(repository) as T
    }
}
