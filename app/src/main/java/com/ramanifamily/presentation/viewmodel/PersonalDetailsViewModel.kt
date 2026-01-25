package com.ramanifamily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.common.Utils
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.data.entity.CommonIdName
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.usecase.GetDistrictsUseCase
import com.ramanifamily.domain.usecase.GetSubDistrictsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonalDetailsViewModel(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val getDistrictsUseCase: GetDistrictsUseCase,
    private val getSubDistrictsUseCase: GetSubDistrictsUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    //Map Proto → UI Model HERE
    val userDetails = userDataStoreRepository.getLoginResponse().map { proto ->
            proto.takeIf { proto.user.id.toString().isNotBlank() }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    //Districts State
    private val _districtsState = MutableStateFlow<ApiState<CommonIdName>>(ApiState.Idle)
    val districtsState: StateFlow<ApiState<CommonIdName>> = _districtsState

    //Sub-Districts State
    private val _subDistrictsState = MutableStateFlow<ApiState<CommonIdName>>(ApiState.Idle)
    val subDistrictsState: StateFlow<ApiState<CommonIdName>> = _subDistrictsState

    // ========================= DISTRICTS =========================
    fun getDistricts(stateId: Int) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _districtsState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _districtsState.value = ApiState.Loading

            try {
                val response = getDistrictsUseCase.execute(stateId)
                if (response.isSuccessful && response.body()?.status == true) {
                    _districtsState.value = ApiState.Success(response.body()!!)
                } else {
//                    val message = ApiErrorParser.parse(response.errorBody()?.string())
                    _districtsState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _districtsState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetDistrictsState() {
        _districtsState.value = ApiState.Idle
    }

    // ========================= SUB-DISTRICTS =========================
    fun getSubDistricts(districtId: Int) {
        viewModelScope.launch {
            if (!networkChecker.isConnected()) {
                _subDistrictsState.value = ApiState.Error(Utils.NO_INTERNET_CONNECTION)
                return@launch
            }
            _subDistrictsState.value = ApiState.Loading

            try {
                val response = getSubDistrictsUseCase.execute(districtId)
                if (response.isSuccessful && response.body()?.status == true) {
                    _subDistrictsState.value = ApiState.Success(response.body()!!)
                } else {
//                    val message = ApiErrorParser.parse(response.errorBody()?.string())
                    _subDistrictsState.value = ApiState.Error(Utils.parseError(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _subDistrictsState.value = ApiState.Error(Utils.SOMETHING_WENT_WRONG)
            }
        }
    }

    fun resetSubDistrictsState() {
        _subDistrictsState.value = ApiState.Idle
    }
}
