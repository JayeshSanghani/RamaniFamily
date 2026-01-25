package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.common.Utils
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.domain.usecase.AddMemberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMemberViewModel(
    private val addMemberUseCase: AddMemberUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _addMemberState = MutableStateFlow<ApiState<AddMemberResponse>>(ApiState.Idle)

    val addMemberState: StateFlow<ApiState<AddMemberResponse>> = _addMemberState

    fun addMember(request: AddMemberRequest) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _addMemberState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }

            _addMemberState.value = ApiState.Loading
            try {
                val response = addMemberUseCase.execute(request)
                if (response.isSuccessful && response.body()?.status == true) {
                    _addMemberState.value = ApiState.Success(response.body()!!)
                } else {
                    _addMemberState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                _addMemberState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetState() {
        _addMemberState.value = ApiState.Idle
    }
}





