package com.example.mobimarket.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import kotlinx.coroutines.launch

class LikedProductViewModel (val repository: Repository): ViewModel() {

    private val _liked_products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val liked_products: LiveData<Resource<List<Product>>>
        get() = _liked_products

    private fun saveProducts(response: List<Product>) {
        _liked_products.postValue(Resource.Success(response))
    }

    fun getLikedProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getLikedProducts()
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveProducts(it) }
                    Log.d("getLikedProducts", "Successful: $productResponse")

                }else{
                    _liked_products.postValue(Resource.Error("Ошибка загрузки моих товаров"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _liked_products.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }
}


class ViewModelProviderFactoryLikedProducts (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedProductViewModel::class.java)) {
            return LikedProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}