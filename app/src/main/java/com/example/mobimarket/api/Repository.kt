package com.example.mobimarket.api

import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.Product
import com.example.mobimarket.model.RegistrationRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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

    suspend fun getMyProducts() = RetrofitInstance.api.getMyProducts()
    suspend fun getLikedProducts() = RetrofitInstance.api.getLikedProducts()
    suspend fun getProductsList() = RetrofitInstance.api.getProductsList()
    fun productDelete(id: Int) = RetrofitInstance.api.productDelete(id)
    fun likeProduct(id: Int) = RetrofitInstance.api.likeProduct(id)
    suspend fun productDetail(id: Int) = RetrofitInstance.api.productDetail(id)
}