package com.example.weatherapplication.model

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepoInterface : RemoteSource , LocalSource {
    var current = Current(0L , null , null , 0.0 ,0.0 , 0L , 0L ,0.0 , 0.0 , 0L , 0L , 0.0 ,
        0L,0.0 , listOf() , null , null )
    var minutely : List<Minutely> = listOf()
    var current1 : List<Current> = listOf()
    var daily : List<Daily> = listOf()
    var myData =Data(33.0 , 33.0 , "egypt" , 30L ,current ,minutely ,current1 ,daily)
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
            x.add(data)
    }

    override suspend fun deleteData(data: Location) {
        x.remove(data)
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

    override suspend fun getData(
        long: Double,
        lat: Double,
        units: String,
        lang: String
    ): Flow<Data> {
        var flowDate= flow {
            emit(myData)
        }
        return flowDate
    }
}