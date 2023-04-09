package com.example.weatherapplication.dB

import androidx.room.*
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao
{
    @Query("SELECT * FROM data")
    fun getAllData() : Flow<List<Location>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: Location)
    @Delete
    suspend fun deleteData(data: Location)
    @Query("SELECT * FROM Date_Time")
    fun getDate() : Flow<List<TimeDate>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDateTime(datetime : TimeDate)
    @Delete
    suspend fun deleteDateTime(datetime : TimeDate)

}