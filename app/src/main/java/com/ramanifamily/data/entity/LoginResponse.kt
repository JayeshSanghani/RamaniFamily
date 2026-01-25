package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class User(@SerializedName("education")
                val education: String = "",
                @SerializedName("occupation")
                val occupation: String = "",
                @SerializedName("gender")
                val gender: String = "",
                @SerializedName("business_address")
                val businessAddress: String = "",
                @SerializedName("created_at")
                val createdAt: String = "",
                @SerializedName("sub_district_name")
                val subDistrictName: String = "",
                @SerializedName("is_admin")
                val isAdmin: Boolean = false,
                @SerializedName("district_name")
                val districtName: String = "",
                @SerializedName("last_donated")
                val lastDonated: String = "",
                @SerializedName("updated_at")
                val updatedAt: String = "",
                @SerializedName("state_name")
                val stateName: String = "",
                @SerializedName("surname")
                val surname: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("state_id")
                val stateId: Int = 0,
                @SerializedName("village")
                val village: String = "",
                @SerializedName("maternal_detail")
                val maternalDetail: String = "",
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("email")
                val email: String = "",
                @SerializedName("sister")
                val sister: Int = 0,
                @SerializedName("height")
                val height: String = "",
                @SerializedName("business_name")
                val businessName: String = "",
                @SerializedName("address")
                val address: String = "",
                @SerializedName("zodiac")
                val zodiac: String = "",
                @SerializedName("mobile")
                val mobile: String = "",
                @SerializedName("weight")
                val weight: String = "",
                @SerializedName("middle_name")
                val middleName: String = "",
                @SerializedName("business_contact")
                val businessContact: String = "",
                @SerializedName("profile_img")
                val profileImg: String = "",
                @SerializedName("marital_status")
                val maritalStatus: String = "",
                @SerializedName("dob")
                val dob: String = "",
                @SerializedName("donate_blood")
                val donateBlood: Boolean = false,
                @SerializedName("sub_district_id")
                val subDistrictId: Int = 0,
                @SerializedName("district")
                val district: String = "",
                @SerializedName("taluka")
                val taluka: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("show_number")
                val showNumber: Boolean = false,
                @SerializedName("property_detail")
                val propertyDetail: String = "",
                @SerializedName("blood_group")
                val bloodGroup: String = "",
                @SerializedName("district_id")
                val districtId: Int = 0,
                @SerializedName("other_detail")
                val otherDetail: String = "",
                @SerializedName("brother")
                val brother: Int = 0)

data class BannersItem(@SerializedName("image")
                       val image: String = "",
                       @SerializedName("id")
                       val id: Int = 0)
data class LoginResponse(@SerializedName("message")
                         val message: String = "",
                         @SerializedName("user")
                         val user: User,
                         @SerializedName("banners")
                         val banners: List<BannersItem>?,
                         @SerializedName("status")
                         val status: Boolean = false,
                         @SerializedName("token")
                         val token: String = "")


