package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class AddMemberResponse(@SerializedName("data")
                             val data: AddMemberData,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("status")
                             val status: Boolean = false)


data class AddMemberData(@SerializedName("notes")
                val notes: String = "",
                @SerializedName("gender")
                val gender: String = "",
                @SerializedName("updated_at")
                val updatedAt: String = "",
                @SerializedName("surname")
                val surname: String = "",
                @SerializedName("family_register_id")
                val familyRegisterId: Int = 0,
                @SerializedName("mobile")
                val mobile: String = "",
                @SerializedName("blood_group")
                val bloodGroup: String = "",
                @SerializedName("created_at")
                val createdAt: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("relation")
                val relation: String = "")


