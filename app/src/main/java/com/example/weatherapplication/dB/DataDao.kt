package com.example.weatherapplication.dB

import androidx.room.*
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import kotlinx.coroutines.flow.Flow
@Dao
interface DataDao
{   @Query("SELECT * FROM data")
    fun getAllData() : List<Location>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: Location)
    @Delete
    suspend fun deleteData(data: Location)
}