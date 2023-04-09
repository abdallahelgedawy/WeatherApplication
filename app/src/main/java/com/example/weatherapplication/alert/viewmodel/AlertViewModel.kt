package com.example.weatherapplication.alert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlertViewModel(private val repo : RepositoryInterface) :  ViewModel(){
    private var mydateTime: MutableLiveData<List<TimeDate>> = MutableLiveData<List<TimeDate>>()
    val finaldateTime: LiveData<List<TimeDate>> = mydateTime
    init {
        getDate()
    }
    private fun getDate() {
        viewModelScope.launch(Dispatchers.IO) {
            mydateTime.postValue(repo.getStoredDate())
        }
    }
    fun addToAlarm(date : TimeDate) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertDateTime(date)
        }

    }


    fun delete(date: TimeDate) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteDateTime(date)
            getDate()
        }
    }
}


