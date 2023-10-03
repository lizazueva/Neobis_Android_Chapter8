package com.example.mobimarket.api

import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.RegistrationRequest

class Repository {
    suspend fun login (request: LoginRequest) = RetrofitInstance.api.login(request)
    suspend fun registration (request: RegistrationRequest) = RetrofitInstance.api.registration(request)

}