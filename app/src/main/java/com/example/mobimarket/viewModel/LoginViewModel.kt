package com.example.mobimarket.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.LoginResponse
import com.example.mobimarket.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel (val repository: Repository): ViewModel() {

    private val _loginResult: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResult: LiveData<Resource<LoginResponse>>
        get() = _loginResult

    private fun loginUser(response: LoginResponse) {
        _loginResult.postValue(Resource.Success(response))
    }
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username, password)
                val response = repository.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let{loginUser(loginResponse)}
                    Log.d("Registration", "Successful: $loginResponse")

                }else{
                    _loginResult.postValue(Resource.Error("Ошибка авторизации"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка авторизации: ${e.message}")
                _loginResult.postValue(Resource.Error(e.message ?: "Ошибка авторизации"))
            }
        }
    }




}

class ViewModelProviderFactoryLogin(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}