package com.example.mobimarket.api

import com.example.mobimarket.model.AddProductResponse
import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.LoginResponse
import com.example.mobimarket.model.RegistrationRequest
import com.example.mobimarket.model.RegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {

    @POST("auth/")
    suspend fun login (@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/profile/")
    suspend fun registration (@Body request: RegistrationRequest): Response<RegistrationResponse>

    @Multipart
    @POST("products/create_product/")
    fun productAdd(@Part image: List<MultipartBody.Part>,
                   @Part("title") title: RequestBody,
                   @Part("price") price: RequestBody,
                   @Part("description") shortDesc: RequestBody,
                   @Part("more_info") fullDesc: RequestBody): Call<AddProductResponse>


}