package com.example.weatherapplication

import com.example.weatherapplication.model.Current
import com.example.weatherapplication.model.Daily
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Minutely
import com.example.weatherapplication.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteSource :RemoteSource{
   var current = Current(0L , null , null , 0.0 ,0.0 , 0L , 0L ,0.0 , 0.0 , 0L , 0L , 0.0 ,
    0L,0.0 , listOf() , null , null )
   var minutely : List<Minutely> = listOf()
    var current1 : List<Current> = listOf()
    var daily : List<Daily> = listOf()
    var myData =Data(33.0 , 33.0 , "egypt" , 30L ,current ,minutely ,current1 ,daily)
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
