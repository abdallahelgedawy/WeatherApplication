package com.example.weatherapplication

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import com.example.weatherapplication.model.RepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeRepository : RepositoryInterface {
    var favLocation:MutableList<Location> = mutableListOf()

    var favAlerts:MutableList<TimeDate> = mutableListOf()
    override suspend fun getData(
        long: Double,
        lat: Double,
        units: String,
        lang: String
    ): Flow<Data> {
        TODO("Not yet implemented")
    }

    override fun getstoredData(): Flow<List<Location>> {
        val flowData= flow {
            val storedLocatios=favLocation.toList()
            if(!storedLocatios.isEmpty()){
                emit(storedLocatios)
            }
        }
        return  flowData
    }


    override suspend fun insertData(data: Location) {
         favLocation.add(data)
    }

    override suspend fun deleteData(data: Location) {
        favLocation.remove(data)
    }

    override suspend fun insertDateTime(datetime: TimeDate) {
        favAlerts.add(datetime)
    }

    override suspend fun deleteDateTime(datetime: TimeDate) {
        favAlerts.remove(datetime)
    }

    override fun getStoredDate(): Flow<List<TimeDate>> {
        val flowData= flow {
            val storedLocatios=favAlerts.toList()
            if(!storedLocatios.isEmpty()){
                emit(storedLocatios)
            }
        }
        return  flowData
    }
    }
