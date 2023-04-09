package com.example.weatherapplication.alert

import android.os.Parcel
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Calendar
@Entity(tableName = "Date_Time")
data class TimeDate (
     @PrimaryKey
     var timeto : String ,
     var timefrom : String,
     var dateto : String,
     var datefrom : String
) : Serializable

