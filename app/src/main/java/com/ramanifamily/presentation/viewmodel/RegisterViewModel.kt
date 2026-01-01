package com.ramanifamily.presentation.viewmodel

import android.content.Context
import androidx.appcompat.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramanifamily.common.Utils
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.common.network.NetworkUtil
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.ProgressUtils
import com.ramanifamily.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ramanifamily.data.remote.ApiErrorParser

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _registerState = MutableStateFlow<ApiState<RegisterResponse>>(ApiState.Idle)
    val registerState: StateFlow<ApiState<RegisterResponse>> = _registerState

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _registerState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _registerState.value = ApiState.Loading

            try {
                val response = registerUserUseCase.execute(request)

                if (response.isSuccessful && response.body()?.status == true) {
                    _registerState.value =  ApiState.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = ApiErrorParser.parse(errorBody)
                    _registerState.value = ApiState.Error(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _registerState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetState() {
        _registerState.value = ApiState.Idle
    }
}




