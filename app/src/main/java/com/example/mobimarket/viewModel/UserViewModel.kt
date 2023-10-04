package com.example.mobimarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.api.Repository

class UserViewModel (private val repository: Repository): ViewModel() {


}

class ViewModelProviderFactoryUser(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}