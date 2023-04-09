package com.example.weatherapplication.dB

import androidx.room.*
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Location

@Dao
interface DataDao
{
    @Query("SELECT * FROM data")
    fun getAllData() : List<Location>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: Location)
    @Delete
    suspend fun deleteData(data: Location)
    @Query("SELECT * FROM Date_Time")
    fun getDate() : List<TimeDate>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDateTime(datetime : TimeDate)
    @Delete
    suspend fun deleteDateTime(datetime : TimeDate)

}