package com.example.mobimarket.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.api.Repository
import com.example.mobimarket.model.RegistrationRequest
import com.example.mobimarket.utils.Resource
import kotlinx.coroutines.launch
import java.io.Serializable

class RegistrationViewModel (private val repository: Repository): ViewModel() {

    private val _userSaved: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val userSaved: LiveData<Resource<Boolean>>
        get() = _userSaved

    private fun saveUserSaved(saved: Boolean) {
        _userSaved.postValue(Resource.Success(saved))
    }

    fun newUser(username: String, email: String, password: String, password2: String) {
        viewModelScope.launch {
            try {
                val userRequest = RegistrationRequest(username, email, password, password2)
                val response = repository.registration(userRequest)
                if (response.isSuccessful) {
                    _userSaved.postValue(Resource.Loading())
                    val responseBody = response.body()
                    saveUserSaved(true)
                    Log.d("Registration", "Successful: $responseBody")
                }else{
                    _userSaved.postValue(Resource.Error("Ошибка регистрации"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка регистрации: ${e.message}")

                _userSaved.postValue(Resource.Error(e.message ?: "Ошибка регистрации"))
            }
        }
    }

}

class ViewModelProviderFactoryRegistration(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}