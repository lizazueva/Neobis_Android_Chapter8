package com.example.mobimarket.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.AddProductResponse
import com.example.mobimarket.utils.LocalProvider
import okhttp3.MediaType
import retrofit2.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class AddProductViewModel(val repository: Repository, val context: Context): ViewModel() {

    fun createProduct(
        images: List<Uri>,
        title: String,
        price: String,
        shortDesc: String,
        fullDesc: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {

//        val fileToSend = prepareFilePart("product_file", images)

        val imageParts = images.mapIndexed { index, uri ->
            prepareFilePart("image[$index]", uri)
        }


//        images.forEachIndexed { _, imageUri ->
//            val file: File? = prepareFilePart("product_file",imageUri)
//            if (file != null) {
//                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
//                val imagePart = MultipartBody.Part.createFormData("images", file.name, requestBody)
//                imageParts.add(imagePart)
//            }
//        }

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

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file: File? = LocalProvider(context).getFileFromUri(fileUri)
        val requestFile: RequestBody? = file?.let {
            RequestBody.create(
                context.contentResolver.getType(fileUri)?.toMediaTypeOrNull(), it
            )
        }
        return requestFile?.let { MultipartBody.Part.createFormData(partName, file.name, it) }
            ?: MultipartBody.Part.createFormData(partName, "")
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