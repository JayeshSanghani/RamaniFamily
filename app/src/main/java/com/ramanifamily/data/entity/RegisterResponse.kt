package com.ramanifamily.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterResponse(@SerializedName("message")
                            val message: String = "",
                            @SerializedName("user")
                            val user: User,
                            @SerializedName("status")
                            val status: Boolean = false,
                            @SerializedName("token")
                            val token: String = "")

