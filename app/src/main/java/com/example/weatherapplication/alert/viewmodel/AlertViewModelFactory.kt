package com.example.weatherapplication.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.favorite.viewModel.FavoriteViewModel
import com.example.weatherapplication.model.RepositoryInterface

class AlertViewModelFactory(private val repo : RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("Class not found")
        }
    }
}