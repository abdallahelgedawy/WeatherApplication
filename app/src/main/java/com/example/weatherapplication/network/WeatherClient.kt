package com.example.weatherapplication.network

import android.util.Log
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.model.Data
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherClient : RemoteSource {

    companion object{
        var repo = WeatherClient()
            fun getInstance(): WeatherClient {
            if (repo == null) {
                repo = WeatherClient()
            }
            return repo
        }
    }



    override suspend fun getData(long : Double , lat : Double , units : String , lang : String): Data {
        var result : Data?= null
        val response = MyApi.service.getWeather(lat , long , units , lang)
        if (response.isSuccessful){
          result = response.body()!!
        }
        return result!!
    }


}
object RetrofitHelper{
    val gson = GsonBuilder().create()
    val myRetrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

object MyApi{
    val service:WeatherService by lazy {
        RetrofitHelper.myRetrofit.create(WeatherService::class.java)
    }

}
