package com.example.weatherapplication.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo : RepositoryInterface) : ViewModel() {
private var data :MutableLiveData<Data> = MutableLiveData()
     val mydata : LiveData<Data> = data
    private var long: Double = 0.0
    private var lat : Double = 0.0
    init {
        getData(long , lat)
    }
    fun getData(long: Double , lat: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(repo.getData(long, lat))
        }
    }



}