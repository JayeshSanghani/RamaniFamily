package com.ramanifamily.presentation.profile

import androidx.compose.runtime.mutableStateListOf
import com.ramanifamily.data.entity.AddMemberRequest

object FamilyMemberStore {
//    val members = mutableStateListOf<FamilyMember>()

    private val _members = mutableStateListOf<AddMemberRequest>()
    val members: List<AddMemberRequest> get() = _members

    fun addMember(member: AddMemberRequest) {
        _members.add(member)
    }

    fun clear() {
        _members.clear()
    }
}