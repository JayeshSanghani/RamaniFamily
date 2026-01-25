package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class MemberListRequest(@SerializedName("user_id")
                             val userId: Int = 0)


