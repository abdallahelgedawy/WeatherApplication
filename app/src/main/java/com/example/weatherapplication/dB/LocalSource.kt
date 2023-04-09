package com.example.weatherapplication.dB

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Location

interface LocalSource {
    fun getstoredData() : List<Location>
    suspend fun insertData(data: Location)
    suspend fun deleteData(data: Location)
    suspend fun insertDateTime(datetime : TimeDate)
    suspend fun deleteDateTime(datetime: TimeDate)
    fun getStoredDate() : List<TimeDate>

}