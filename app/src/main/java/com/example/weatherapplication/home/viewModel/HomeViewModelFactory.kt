package com.example.weatherapplication.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.model.RepositoryInterface

class HomeViewModelFactory(private val repo : RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repo) as T
        } else{
            throw java.lang.IllegalArgumentException("Class not found")
        }
    }

}
