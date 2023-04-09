package com.example.weatherapplication.dB

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getstoredData() : Flow<List<Location>>
    suspend fun insertData(data: Location)
    suspend fun deleteData(data: Location)
    suspend fun insertDateTime(datetime : TimeDate)
    suspend fun deleteDateTime(datetime: TimeDate)
    fun getStoredDate() : Flow<List<TimeDate>>

}