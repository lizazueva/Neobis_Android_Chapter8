package com.example.mobimarket.api

import com.example.mobimarket.utils.Constants.Companion.BASE_URL
import com.example.mobimarket.utils.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthorizationInterceptor())
            .build()

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Api by lazy {
            retrofit.create(Api::class.java)
        }

        private class AuthorizationInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request = chain.request()
                val newRequest = if (requiresAuthorization(request)) {
                    val token = Utils.access_token
                    val authHeader = "Bearer $token"
                    request.newBuilder()
                        .header("Authorization", authHeader)
                        .build()
                } else {
                    request
                }
                return chain.proceed(newRequest)
            }

            private fun requiresAuthorization(request: okhttp3.Request): Boolean {
                val path = request.url.encodedPath
                return path.endsWith("products/create_product/")||
                        path.endsWith("products/my_products_list/")||
                        path.contains("products/delete_product/") && request.method == "DELETE"||
                        path.contains("products/detail_product/") && request.method == "GET"||
                        path.endsWith("products/favorite_products/")||
                        path.contains("favorite/toggle_favorite/") && request.method == "POST"||
                        path.contains("products/update_product/") && request.method == "PUT"
            }
        }
    }
}