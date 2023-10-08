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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsListViewModel (val repository: Repository): ViewModel() {

    private val _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: LiveData<Resource<List<Product>>>
        get() = _products

    private fun saveProducts(response: List<Product>) {
        _products.postValue(Resource.Success(response))
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getProductsList()
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveProducts(it) }
                    Log.d("getMyProducts", "Successful: $productResponse")

                }else{
                    _products.postValue(Resource.Error("Ошибка загрузки моих товаров"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _products.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

    fun likeProduct(onSuccess: () -> Unit,
                      onError: (String?) -> Unit,
                      id: Int){
        repository.likeProduct(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Ошибка при выполнении запроса: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("ProductsListViewModel", "Ошибка при выполнении запроса", t)
                onError("")
            }

        })

    }

}

class ViewModelProviderFactoryProducts (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            return ProductsListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}