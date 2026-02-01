package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class ProfileBusinessRequest(@SerializedName("business_name")
                                  val businessName: String = "",
                                  @SerializedName("business_address")
                                  val businessAddress: String = "",
                                  @SerializedName("show_number")
                                  val showNumber: Boolean = false,
                                  @SerializedName("other_detail")
                                  val otherDetail: String = "",
                                  @SerializedName("business_contact")
                                  val businessContact: String = "")


