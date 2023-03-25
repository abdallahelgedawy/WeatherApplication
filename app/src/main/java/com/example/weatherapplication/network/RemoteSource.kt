package com.example.weatherapplication.network

import com.example.weatherapplication.model.Data

interface RemoteSource {
    suspend fun getData(long : Double , lat : Double) : Data
}