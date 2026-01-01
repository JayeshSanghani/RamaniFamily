package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramanifamily.data.remote.AppModule

class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            AppModule.userDataStoreRepository
        ) as T
    }
}
