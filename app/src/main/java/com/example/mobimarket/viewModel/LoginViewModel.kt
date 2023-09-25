package com.example.mobimarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository

class LoginViewModel (val repository: Repository): ViewModel() {


}

class ViewModelProviderFactoryLogin(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelProviderFactoryLogin::class.java)) {
            return ViewModelProviderFactoryLogin(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}