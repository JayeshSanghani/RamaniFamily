package com.ramanifamily.data.api

import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.entity.AddMemberResponse
import com.ramanifamily.data.entity.CommonIdName
import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.entity.MemberListRequest
import com.ramanifamily.data.entity.MemberListResponse
import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.entity.ProfilePersonalRequest
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    @GET("districts/{stateId}")
    suspend fun getDistricts(@Path("stateId") stateId: Int): Response<CommonIdName>

    @GET("sub-districts/{districtId}")
    suspend fun getSubDistricts(@Path("districtId") districtId: Int): Response<CommonIdName>

    @POST("family-member/add")
    suspend fun addMember(@Body body: AddMemberRequest): Response<AddMemberResponse>

    @POST("family/list")
    suspend fun memberList(@Body body: MemberListRequest): Response<MemberListResponse>

    @POST("profile/personal")
    suspend fun profilePersonal(@Body body: ProfilePersonalRequest): Response<ProfileResponse>

    @POST("profile/business")
    suspend fun profileBusiness(@Body body: ProfileBusinessRequest): Response<ProfileResponse>

    @POST("profile/marital")
    suspend fun profileMarital(@Body body: ProfileMaritalRequest): Response<ProfileResponse>
}