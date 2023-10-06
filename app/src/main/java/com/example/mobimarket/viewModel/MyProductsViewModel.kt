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

class MyProductsViewModel (val repository: Repository): ViewModel() {

        private val _my_products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
        val my_products: LiveData<Resource<List<Product>>>
            get() = _my_products

        private fun saveProducts(response: List<Product>) {
            _my_products.postValue(Resource.Success(response))
        }

        fun getMyProducts() {
            viewModelScope.launch {
                try {
                    val response = repository.getMyProducts()
                    if (response.isSuccessful) {
                        val productResponse = response.body()
                        productResponse?.let { saveProducts(it) }
                        Log.d("getMyProducts", "Successful: $productResponse")

                    }else{
                        _my_products.postValue(Resource.Error("Ошибка загрузки моих товаров"))
                    }
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                    _my_products.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
                }
            }
        }




    }

    class ViewModelProviderFactoryMyProducts (private val repository: Repository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyProductsViewModel::class.java)) {
                return MyProductsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }