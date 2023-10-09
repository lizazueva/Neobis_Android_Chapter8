package com.example.mobimarket.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.AddProductResponse
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.LocalProvider
import retrofit2.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class AddProductViewModel(val repository: Repository, val context: Context): ViewModel() {

    fun createProduct(
        image: List<Uri>,
        title: String,
        price: String,
        shortDesc: String,
        fullDesc: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {


        val imageParts = mutableListOf<MultipartBody.Part>()

        image.forEachIndexed { _, image ->
            val file: File? = LocalProvider.getFile(context, image)
            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = requestBody?.let {
                MultipartBody.Part.createFormData("image", file.name, it)
            }
            imagePart?.let { imageParts.add(it) }
        }


        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val pricePart = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val shortDescPart = shortDesc.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullDescPart = fullDesc.toRequestBody("text/plain".toMediaTypeOrNull())

        repository.productAdd(imageParts, titlePart, pricePart, shortDescPart, fullDescPart)
            .enqueue(object : Callback<AddProductResponse> {
                override fun onResponse(
                    call: Call<AddProductResponse>,
                    response: Response<AddProductResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Ошибка при выполнении запроса: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                    Log.e("AddProductViewModel", "Ошибка при выполнении запроса", t)
                    onError("")
                }
            })
    }

    fun productUpdate(
        id: Int,
        image: List<Uri>,
        title: String,
        price: String,
        shortDesc: String,
        fullDesc: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {


        val imageParts = mutableListOf<MultipartBody.Part>()

        image.forEachIndexed { _, image ->
            val file: File? = LocalProvider.getFile(context, image)
            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = requestBody?.let {
                MultipartBody.Part.createFormData("image", file.name, it)
            }
            imagePart?.let { imageParts.add(it) }
        }


        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val pricePart = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val shortDescPart = shortDesc.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullDescPart = fullDesc.toRequestBody("text/plain".toMediaTypeOrNull())

        repository.productUpdate(id, imageParts, titlePart, pricePart, shortDescPart, fullDescPart)
            .enqueue(object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Ошибка при выполнении запроса: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Log.e("AddProductViewModel", "Ошибка при выполнении запроса", t)
                    onError("")
                }
            })
    }



    class ViewModelProviderFactoryAddProduct(
        private val repository: Repository,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
                return AddProductViewModel(repository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}