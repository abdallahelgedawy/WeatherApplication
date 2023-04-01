package com.example.weatherapplication.dB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location


@Database(entities = arrayOf(Location::class), version = 2 )
abstract class DataDatabase : RoomDatabase(){
    abstract fun getDataDao(): DataDao
    companion object {
        @Volatile
        private var INSTANCE: DataDatabase? = null
        fun getInstance(ctx: Context): DataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, DataDatabase::class.java, "data").build()
                INSTANCE = instance
                instance
            }
        }
    }
}