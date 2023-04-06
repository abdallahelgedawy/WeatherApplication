package com.example.weatherapplication.favorite.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import com.example.weatherapplication.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repo : RepositoryInterface) : ViewModel() {
    private var myData: MutableLiveData<List<Location>> = MutableLiveData<List<Location>>()
    val finalData: LiveData<List<Location>> = myData

    init {
        getFavData()
    }

    private fun getFavData() {
        viewModelScope.launch(Dispatchers.IO) {
            myData.postValue(repo.getstoredData())
        }
    }

    fun delete(data: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteData(data)
            getFavData()
        }
    }

    fun addToFavorite(long : Double , lat : Double ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(Location(lat , long))
            repo.getData(lat , long , units = "metric" , lang = "ar")
        }

    }
}

