package com.ramanifamily.data.api

import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.entity.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

//    @GET("users")
//    suspend fun getUsers(): Response<List<UserEntity>>
}