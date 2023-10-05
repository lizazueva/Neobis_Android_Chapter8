package com.example.mobimarket.api

import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.RegistrationRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

class Repository {
    suspend fun login (request: LoginRequest) = RetrofitInstance.api.login(request)
    suspend fun registration (request: RegistrationRequest) = RetrofitInstance.api.registration(request)

    fun productAdd(@Part image: List<MultipartBody.Part>,
                   @Part("title") title: RequestBody,
                   @Part("price") price: RequestBody,
                   @Part("description") shortDesc: RequestBody,
                   @Part("more_info") fullDesc: RequestBody
    ) = RetrofitInstance.api.productAdd(image, title, price, shortDesc, fullDesc)
}