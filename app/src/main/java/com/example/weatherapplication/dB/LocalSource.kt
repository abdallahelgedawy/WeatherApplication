package com.example.weatherapplication.dB

import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getstoredData() : List<Location>
    suspend fun insertData(data: Location)
    suspend fun deleteData(data: Location)
}