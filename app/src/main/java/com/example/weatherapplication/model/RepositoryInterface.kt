package com.example.weatherapplication.model

import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.network.RemoteSource

interface RepositoryInterface : RemoteSource  , LocalSource{
}