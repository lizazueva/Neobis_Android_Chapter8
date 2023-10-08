package com.example.mobimarket.api

import com.example.mobimarket.model.AddProductResponse
import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.LoginResponse
import com.example.mobimarket.model.Product
import com.example.mobimarket.model.RegistrationRequest
import com.example.mobimarket.model.RegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("products/my_products_list/")
    suspend fun getMyProducts(): Response<List<Product>>

    @GET("products/list/")
    suspend fun getProductsList(): Response<List<Product>>

    @DELETE("products/delete_product/{id}")
    fun productDelete(@Path("id") id: Int): Call<Unit>

    @GET ("products/detail_product/{id}")
    suspend fun productDetail(@Path("id") id: Int): Response<Product>



}