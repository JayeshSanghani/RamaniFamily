package com.ramanifamily.data.entity

import com.google.gson.annotations.SerializedName

data class LoginRequest(@SerializedName("password")
                        val password: String = "",
                        @SerializedName("mobile")
                        val mobile: String = "")