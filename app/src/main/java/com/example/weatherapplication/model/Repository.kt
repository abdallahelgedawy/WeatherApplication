package com.example.weatherapplication.model

import com.example.weatherapplication.network.RemoteSource

class Repository private constructor(var remoteSource: RemoteSource) : RepositoryInterface{
    companion object{
        @Volatile
        private var Instance : Repository?= null
        fun getInstance(RS: RemoteSource ): Repository {
            return Instance?: synchronized(this){
                val temp = Repository(RS)
                Instance=temp
                temp
            }
        }
    }

    override suspend fun getData(lat : Double , long : Double): Data {
        return remoteSource.getData(lat , long)
    }

}