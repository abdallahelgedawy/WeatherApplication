package com.example.weatherapplication

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalSourceTest : LocalSource {
    var x:MutableList<Location> = mutableListOf()
    override fun getstoredData(): Flow<List<Location>> {
        val flowData= flow {
            val storedLocatios=x.toList()
            if(!storedLocatios.isEmpty()){
                emit(storedLocatios)
            }
        }
        return  flowData
    }

    override suspend fun insertData(data: Location) {

    }

    override suspend fun deleteData(data: Location) {

    }

    override suspend fun insertDateTime(datetime: TimeDate) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDateTime(datetime: TimeDate) {
        TODO("Not yet implemented")
    }

    override fun getStoredDate(): Flow<List<TimeDate>> {
        TODO("Not yet implemented")
    }
}