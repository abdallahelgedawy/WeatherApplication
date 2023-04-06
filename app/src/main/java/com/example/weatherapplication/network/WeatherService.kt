package com.example.weatherapplication.network

import com.example.weatherapplication.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

@GET("onecall")
suspend fun getWeather(@Query("lat") lat:Double   ,
    @Query("lon") lon: Double  ,
    @Query("units") units :String ,
    @Query("lang") lang: String ,
    @Query("appid") appid:String = "c0e2a3fc4f1e96050875af44033af784" ): Response<Data>
}