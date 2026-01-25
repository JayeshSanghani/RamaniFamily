package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class DataItem(@SerializedName("name")
                    val name: String = "",
                    @SerializedName("id")
                    val id: Int = 0)


data class CommonIdName(@SerializedName("data")
                        val data: List<DataItem>?,
                        @SerializedName("status")
                        val status: Boolean = false)


