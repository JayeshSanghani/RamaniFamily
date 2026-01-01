package com.ramanifamily.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("village_id")
    val villageId: Int = 0,
    @SerializedName("taluka_id")
    val talukaId: Int = 0,
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("middle_name")
    val middleName: String = "",
    @SerializedName("marital_status")
    val maritalStatus: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("surname")
    val surname: String = "",
    @SerializedName("district")
    val district: String = "",
    @SerializedName("taluka")
    val taluka: String = "",
    @SerializedName("blood_group")
    val bloodGroup: String = "",
    @SerializedName("district_id")
    val districtId: Int = 0,
    @SerializedName("village")
    val village: String = "",
    @SerializedName("first_name")
    val firstName: String = ""
)
