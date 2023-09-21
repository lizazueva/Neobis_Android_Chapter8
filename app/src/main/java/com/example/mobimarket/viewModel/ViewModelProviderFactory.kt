package com.example.mobimarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository

class ViewModelProviderFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> {
                RegistrationViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}