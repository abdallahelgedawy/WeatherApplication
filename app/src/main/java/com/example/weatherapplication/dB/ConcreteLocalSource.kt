package com.example.weatherapplication.dB

import android.content.Context
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location

class ConcreteLocalSource private constructor(val context: Context) : LocalSource{
    private val dataDao : DataDao
    init {
        val db: DataDatabase = DataDatabase.getInstance(context.applicationContext)
        dataDao = db.getDataDao()
    }
    companion object {
        @Volatile
        private var INSTANCE: ConcreteLocalSource? = null

        fun getInstance(context: Context): ConcreteLocalSource {
            return INSTANCE ?: synchronized(this) {
                val temp = ConcreteLocalSource(context)
                INSTANCE = temp
                temp
            }
        }
    }

    override fun getstoredData(): List<Location> {
        return dataDao.getAllData()
    }

    override suspend fun insertData(data: Location) {
        dataDao.insertData(data)
    }

    override suspend fun deleteData(data: Location) {
        dataDao.deleteData(data)
    }
}