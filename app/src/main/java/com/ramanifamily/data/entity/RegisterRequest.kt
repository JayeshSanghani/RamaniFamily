package com.ramanifamily.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("middle_name")
    val middleName: String = "",
    @SerializedName("sub_district_name")
    val subDistrictName: String = "",
    @SerializedName("district_name")
    val districtName: String = "",
    @SerializedName("marital_status")
    val maritalStatus: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("state_name")
    val stateName: String = "",
    @SerializedName("surname")
    val surname: String = "",
    @SerializedName("sub_district_id")
    val subDistrictId: String = "",
    @SerializedName("blood_group")
    val bloodGroup: String = "",
    @SerializedName("state_id")
    val stateId: String = "",
    @SerializedName("district_id")
    val districtId: String = "",
    @SerializedName("village")
    val village: String = "",
    @SerializedName("first_name")
    val firstName: String = ""
)
