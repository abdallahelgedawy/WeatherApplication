package com.example.weatherapplication.network

import com.example.weatherapplication.model.Data


sealed class ApiState{
    class Success(val x : Data ) : ApiState()
    class Failed(val msg : Throwable) : ApiState()
    object loading : ApiState()
}
