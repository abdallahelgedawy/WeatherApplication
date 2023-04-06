package com.example.weatherapplication.model

import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.network.RemoteSource

class Repository private constructor(var remoteSource: RemoteSource , var localSource: LocalSource) : RepositoryInterface{
    companion object{
        @Volatile
        private var Instance : Repository?= null
        fun getInstance(RS: RemoteSource , LS : LocalSource): Repository {
            return Instance?: synchronized(this){
                val temp = Repository(RS , LS)
                Instance=temp
                temp
            }
        }
    }

    override suspend fun getData(lat : Double , long : Double , units : String , lang : String): Data {
        return remoteSource.getData(lat , long , units , lang)
    }

    override fun getstoredData(): List<Location> {
        return localSource.getstoredData()
    }

    override suspend fun insertData(data: Location) {
        localSource.insertData(data)
    }

    override suspend fun deleteData(data: Location) {
        localSource.deleteData(data)
    }

}