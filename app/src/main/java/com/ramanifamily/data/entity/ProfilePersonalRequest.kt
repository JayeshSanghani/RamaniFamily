package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class ProfilePersonalRequest(@SerializedName("address")
                                  val address: String = "",
                                  @SerializedName("gender")
                                  val gender: String = "",
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
                                  @SerializedName("last_donated")
                                  val lastDonated: String = "",
                                  @SerializedName("state_name")
                                  val stateName: String = "",
                                  @SerializedName("surname")
                                  val surname: String = "",
                                  @SerializedName("dob")
                                  val dob: String = "",
                                  @SerializedName("donate_blood")
                                  val donateBlood: Boolean = false,
                                  @SerializedName("sub_district_id")
                                  val subDistrictId: Int = 0,
                                  @SerializedName("show_number")
                                  val showNumber: Boolean = false,
                                  @SerializedName("blood_group")
                                  val bloodGroup: String = "",
                                  @SerializedName("state_id")
                                  val stateId: Int = 0,
                                  @SerializedName("district_id")
                                  val districtId: Int = 0,
                                  @SerializedName("village")
                                  val village: String = "",
                                  @SerializedName("first_name")
                                  val firstName: String = "",
                                  @SerializedName("email")
                                  val email: String = "",
                                  @SerializedName("age")
                                  val age: Int = 0)



