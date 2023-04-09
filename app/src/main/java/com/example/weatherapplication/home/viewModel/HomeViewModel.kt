package com.example.weatherapplication.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.RepositoryInterface
import com.example.weatherapplication.network.ApiState
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repo : RepositoryInterface) : ViewModel() {
//private var data :MutableLiveData<Data> = MutableLiveData()
  //   val mydata : LiveData<Data> = data
    private val data :MutableStateFlow<ApiState> = MutableStateFlow(ApiState.loading)
    val mydata = data.asStateFlow()
    private var long: Double = 0.0
    private var lat : Double = 0.0
    private var units : String = ""
    private var lang : String = ""
    init {
        getData(long , lat , units, lang)
    }
    fun getData(long: Double , lat: Double , units : String , lang : String) {
        viewModelScope.launch(Dispatchers.IO) {
           // data.postValue(repo.getData(long, lat, units , lang))
            repo.getData(long , lat , units , lang).catch {
                e->data.value = ApiState.Failed(e)
            }.collect{
                    x -> data.value = ApiState.Success(x)
            }

        }
    }



}