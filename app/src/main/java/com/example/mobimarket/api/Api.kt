package com.example.mobimarket.api

import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.LoginResponse
import com.example.mobimarket.model.RegistrationRequest
import com.example.mobimarket.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/auth/")
    suspend fun login (@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/profile/")
    suspend fun registration (@Body request: RegistrationRequest): Response<RegistrationResponse>
}