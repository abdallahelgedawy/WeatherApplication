package com.example.weatherapplication.network

import com.example.weatherapplication.model.Data
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
    suspend fun getData(long : Double , lat : Double,  units : String , lang : String ) : Flow<Data>
}