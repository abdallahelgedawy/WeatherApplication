package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapplication.network.WeatherClient
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*


const val Permission_ID = 15

class MainActivity : AppCompatActivity() {

    lateinit var myFusedLocationClient: FusedLocationProviderClient
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        val navController = findNavController(R.id.fragmentContainerView4)
        bottomNavigationView.setupWithNavController(navController)

        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch(Dispatchers.Main) {
            var job1 = launch { getLastLocation() }
            job1.join()
            var job2 = launch { Log.i("gedo", "${longitude.toString()}  ${latitude.toString()}$x") }
        }
    }

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    private fun CheckPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return result
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            Permission_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val myLocationRequest = LocationRequest()
        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        myLocationRequest.setInterval(0)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        myFusedLocationClient.requestLocationUpdates(
            myLocationRequest,
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    val myLastLocation: Location = p0!!.lastLocation
                    latitude = myLastLocation.latitude
                    longitude = myLastLocation.longitude
                    Log.i("TAG", "onLocationResult: " + latitude)

                }
            },
            Looper.myLooper()
        )

    }

    private fun getLastLocation() {
        if (CheckPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                Toast.makeText(this, "Turn on the location please", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            requestPermissions()
        }
    }

}