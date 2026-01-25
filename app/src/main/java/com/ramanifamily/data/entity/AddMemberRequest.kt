package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class AddMemberRequest(@SerializedName("notes")
                            val notes: String = "",
                            @SerializedName("gender")
                            val gender: String = "",
                            @SerializedName("surname")
                            val surname: String = "",
                            @SerializedName("mobile")
                            val mobile: String = "",
                            @SerializedName("blood_group")
                            val bloodGroup: String = "",
                            @SerializedName("first_name")
                            val firstName: String = "",
                            @SerializedName("relation")
                            val relation: String = ""){
    val fullName: String
        get() = listOf(firstName, surname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
}


