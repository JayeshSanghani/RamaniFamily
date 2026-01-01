package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.common.Utils
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.remote.ApiErrorParser
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.usecase.LoginUserUseCase
import com.ramanifamily.data.remote.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val networkChecker: NetworkChecker,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<ApiState<LoginResponse>>(ApiState.Idle)
    val loginState: StateFlow<ApiState<LoginResponse>> = _loginState

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _loginState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _loginState.value = ApiState.Loading

            try {
                val response = loginUserUseCase.execute(request)

                if (response.isSuccessful && response.body()?.status == true) {

                    // SAVE TO DATASTORE HERE
                    val loginResponse = response.body()!!
                    userDataStoreRepository.saveLoginResponse(loginResponse)

                    _loginState.value =  ApiState.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = ApiErrorParser.parse(errorBody)
                    _loginState.value = ApiState.Error(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetState() {
        _loginState.value = ApiState.Idle
    }
}




