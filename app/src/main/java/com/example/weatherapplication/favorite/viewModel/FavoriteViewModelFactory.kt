package com.example.weatherapplication.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.model.RepositoryInterface

class FavoriteViewModelFactory(private val repo : RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("Class not found")
        }
    }
}
