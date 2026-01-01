package com.ramanifamily.presentation.profile

import android.net.Uri

data class FamilyMember(
    val image: Uri?,
    val fullName: String,
    val relation: String
)