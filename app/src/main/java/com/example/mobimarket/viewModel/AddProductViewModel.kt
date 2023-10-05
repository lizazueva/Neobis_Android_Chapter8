package com.example.mobimarket.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.AddProductResponse
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

//        var imageParts = mutableListOf<MultipartBody.Part>()
//
//        image.forEachIndexed { index, imageUri ->
//            val filePart: MultipartBody.Part? = prepareFilePart("image", imageUri)
//            filePart?.let { imageParts.add(it) }
//        }

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

//    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part? {
//        val context = context.applicationContext
//        val contentResolver = context.contentResolver
//
//        val inputStream = contentResolver.openInputStream(fileUri)
//        inputStream?.use { input ->
//            val tempFile = File(context.cacheDir, "temp_image_file")
//            tempFile.deleteOnExit()
//            tempFile.outputStream().use { output ->
//                input.copyTo(output)
//            }
//            val requestBody = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
//            return MultipartBody.Part.createFormData(partName, tempFile.name, requestBody)
//        }
//        return null
//    }



//    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part? {
//        val context = context.applicationContext
//        val contentResolver = context.contentResolver
//
//        val inputStream = contentResolver.openInputStream(fileUri)
//        inputStream?.use { input ->
//            val tempFile = File(context.cacheDir, "temp_image_file")
//            tempFile.deleteOnExit()
//            tempFile.outputStream().use { output ->
//                input.copyTo(output)
//            }
//            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString())
//            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
//            val requestBody = tempFile.asRequestBody(mimeType?.toMediaTypeOrNull())
//            return MultipartBody.Part.createFormData(partName, tempFile.name, requestBody)
//        }
//        return null
//    }


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

//private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
//        val file: File? = LocalProvider(context).getFile(context,fileUri)
//        val requestFile: RequestBody? = file?.let {
//            RequestBody.create(
//                context.contentResolver.getType(fileUri)?.toMediaTypeOrNull(), it
//            )
//        }
//        return requestFile?.let { MultipartBody.Part.createFormData(partName, file.name, it) }
//            ?: MultipartBody.Part.createFormData(partName, "")
//    }