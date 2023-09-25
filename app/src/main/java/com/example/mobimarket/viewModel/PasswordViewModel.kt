package com.example.mobimarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository

class PasswordViewModel (private val repository: Repository): ViewModel() {
}

class ViewModelProviderFactoryPassword(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelProviderFactoryPassword::class.java)) {
            return ViewModelProviderFactoryPassword(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}