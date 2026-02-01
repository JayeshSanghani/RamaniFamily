package com.ramanifamily.data.entity


import com.google.gson.annotations.SerializedName

data class ProfileMaritalRequest(@SerializedName("education")
                                 val education: String = "",
                                 @SerializedName("occupation")
                                 val occupation: String = "",
                                 @SerializedName("zodiac")
                                 val zodiac: String = "",
                                 @SerializedName("property_detail")
                                 val propertyDetail: String = "",
                                 @SerializedName("weight")
                                 val weight: String = "",
                                 @SerializedName("maternal_detail")
                                 val maternalDetail: String = "",
                                 @SerializedName("brother")
                                 val brother: Int = 0,
                                 @SerializedName("sister")
                                 val sister: Int = 0,
                                 @SerializedName("height")
                                 val height: String = "")


