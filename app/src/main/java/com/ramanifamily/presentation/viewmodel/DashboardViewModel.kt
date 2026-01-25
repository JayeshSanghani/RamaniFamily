package com.ramanifamily.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanifamily.data.entity.DashboardUiState
import com.ramanifamily.data.entity.MemberListRequest
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.repository.MemberListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DashboardViewModel(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val memberListRepository: MemberListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadDashboard()
    }

    fun refreshMembers() {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val login = userDataStoreRepository.getLoginResponse().first()

                val request = MemberListRequest(
                    userId = login.user.id
                )

                // Retrofit call
                val response = memberListRepository.memberList(request)

                if (response.isSuccessful) {
                    val body = response.body()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            profileImage = login.user.profileImg ?: "",
                            bannerImages = login.bannersList.map { it.image },
                            members = body?.data ?: emptyList()
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load members"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Something went wrong"
                    )
                }
            }
        }
    }
}
