package com.example.weatherapplication.dB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.model.Location


@Database(entities = [Location::class ,TimeDate::class] , version = 6 )
abstract class DataDatabase : RoomDatabase(){
    abstract fun getDataDao(): DataDao
    companion object {
        @Volatile
        private var INSTANCE: DataDatabase? = null
        fun getInstance(ctx: Context): DataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, DataDatabase::class.java, "data"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}