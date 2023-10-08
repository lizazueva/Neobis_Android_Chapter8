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

class DetailViewModel (val repository: Repository): ViewModel() {

    private val _product: MutableLiveData<Resource<Product>> = MutableLiveData()
    val product: LiveData<Resource<Product>>
        get() = _product

    private fun saveProduct(response: Product) {
        _product.postValue(Resource.Success(response))
    }

    fun productDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.productDetail(id)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveProduct(it) }
                    Log.d("getMyProducts", "Successful: $productResponse")
                }else{
                    _product.postValue(Resource.Error("Ошибка загрузки товарa"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _product.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

}

class ViewModelProviderFactoryDetail (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}