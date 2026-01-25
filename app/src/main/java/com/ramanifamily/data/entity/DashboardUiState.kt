package com.ramanifamily.data.entity

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val profileImage: String = "",
    val bannerImages: List<String> = emptyList(),
    val members: List<MemberListDataItem> = emptyList()
)
