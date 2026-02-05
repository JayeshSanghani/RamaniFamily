package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.common.Utils
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.entity.ProfilePersonalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.repository.ProfileBusinessRepository
import com.ramanifamily.domain.repository.ProfileMaritalRepository
import com.ramanifamily.domain.repository.ProfilePersonalRepository
import com.ramanifamily.domain.usecase.ProfileBusinessUseCase
import com.ramanifamily.domain.usecase.ProfileMaritalUseCase
import com.ramanifamily.domain.usecase.ProfilePersonalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val profilePersonalUseCase: ProfilePersonalUseCase,
    private val profileBusinessUseCase: ProfileBusinessUseCase,
    private val profileMaritalUseCase: ProfileMaritalUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    val userProfile = userDataStoreRepository.getLoginResponse()

    //Map Proto → UI Model HERE
    val userDetails = userDataStoreRepository.getLoginResponse().map { proto ->
        proto.takeIf { proto.user.id.toString().isNotBlank() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            userDataStoreRepository.clear()
            onComplete()
        }
    }

    private val _profilePersonalState = MutableStateFlow<ApiState<ProfileResponse>>(ApiState.Idle)
    val profilePersonalState: StateFlow<ApiState<ProfileResponse>> = _profilePersonalState

    private val _profileBusinessState = MutableStateFlow<ApiState<ProfileResponse>>(ApiState.Idle)
    val profileBusinessState: StateFlow<ApiState<ProfileResponse>> = _profileBusinessState

    private val _profileMaritalState = MutableStateFlow<ApiState<ProfileResponse>>(ApiState.Idle)
    val profileMaritalState: StateFlow<ApiState<ProfileResponse>> = _profileMaritalState

    fun personalState(request: ProfilePersonalRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _profilePersonalState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _profilePersonalState.value = ApiState.Loading

            try {
                val response = profilePersonalUseCase.execute(request)
                if (response.isSuccessful && response.body()?.status == true) {
                    val profileResponse = response.body()!!
                    userDataStoreRepository.saveProfileResponse(profileResponse)
                    _profilePersonalState.value =  ApiState.Success(profileResponse)
                } else {
                    _profilePersonalState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _profilePersonalState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetPersonalState() {
        _profilePersonalState.value = ApiState.Idle
    }

    fun businessState(request: ProfileBusinessRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _profileBusinessState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _profileBusinessState.value = ApiState.Loading

            try {
                val response = profileBusinessUseCase.execute(request)
                if (response.isSuccessful && response.body()?.status == true) {
                    val profileResponse = response.body()!!
                    userDataStoreRepository.saveProfileResponse(profileResponse)
                    _profileBusinessState.value =  ApiState.Success(profileResponse)
                } else {
                    _profileBusinessState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _profileBusinessState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetBusinessState() {
        _profileBusinessState.value = ApiState.Idle
    }

    fun maritalState(request: ProfileMaritalRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _profileMaritalState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _profileMaritalState.value = ApiState.Loading

            try {
                val response = profileMaritalUseCase.execute(request)
                if (response.isSuccessful && response.body()?.status == true) {
                    val profileResponse = response.body()!!
                    userDataStoreRepository.saveProfileResponse(profileResponse)
                    _profileMaritalState.value =  ApiState.Success(profileResponse)
                } else {
                    _profileMaritalState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _profileMaritalState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetMaritalState() {
        _profileMaritalState.value = ApiState.Idle
    }


}
