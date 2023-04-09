package com.example.weatherapplication.model

import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.network.RemoteSource
import kotlinx.coroutines.flow.Flow

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

    override suspend fun getData(lat : Double , long : Double , units : String , lang : String): Flow<Data> {
        return remoteSource.getData(lat , long , units , lang)
    }

    override fun getstoredData(): Flow<List<Location>> {
        return localSource.getstoredData()
    }

    override suspend fun insertData(data: Location) {
        localSource.insertData(data)
    }

    override suspend fun deleteData(data: Location) {
        localSource.deleteData(data)
    }

    override suspend fun insertDateTime(datetime: TimeDate) {
      localSource.insertDateTime(datetime)
    }

    override suspend fun deleteDateTime(datetime: TimeDate) {
         localSource.deleteDateTime(datetime)
    }

    override fun getStoredDate(): Flow<List<TimeDate>> {
        return localSource.getStoredDate()
    }

}