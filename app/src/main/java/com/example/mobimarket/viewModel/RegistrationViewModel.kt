package com.example.mobimarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository

class RegistrationViewModel (private val repository: Repository): ViewModel() {

}

class ViewModelProviderFactoryRegistration(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelProviderFactoryRegistration::class.java)) {
            return ViewModelProviderFactoryRegistration(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}