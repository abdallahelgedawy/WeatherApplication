package com.example.weatherapplication.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.maps.MapsFragment
import com.example.weatherapplication.R
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.dB.DataDao
import com.example.weatherapplication.home.viewModel.HomeViewModel
import com.example.weatherapplication.home.viewModel.HomeViewModelFactory
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.ApiState
import com.example.weatherapplication.network.WeatherClient
import com.example.weatherapplication.settings.Settings1
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



const val Permission_ID = 15

class Home : Fragment() {
    private lateinit var myLocationRequest: LocationRequest
    lateinit var country: TextView
    lateinit var description: TextView
    lateinit var icon1: ImageView
    lateinit var temp: TextView
    lateinit var d_t: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HoursAdapter
    lateinit var mylayoutManager: LinearLayoutManager
    lateinit var img_humidity: ImageView
    lateinit var desc_hum: TextView
    lateinit var img_pressure: ImageView
    lateinit var descpress: TextView
    lateinit var img_wind: ImageView
    lateinit var desc_wind: TextView
    lateinit var img_cloud: ImageView
    lateinit var desc_cloud: TextView
    lateinit var img_uvi: ImageView
    lateinit var desc_uvi: TextView
    lateinit var img_vis: ImageView
    lateinit var desc_vis: TextView
    lateinit var recyclervieweeks: RecyclerView
    lateinit var adapterweeks: WeeksAdapter
    lateinit var mylayoutManagerweeks: LinearLayoutManager
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var myFusedLocationClient: FusedLocationProviderClient
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var mapsFragment: MapsFragment
    lateinit var shared: SharedPreferences
    lateinit var dialogShared: SharedPreferences
    lateinit var langShared: SharedPreferences
    lateinit var settings: Settings1
    lateinit var key1: String
    lateinit var key2: String
    lateinit var lan: String
    lateinit var Temp_shared: SharedPreferences
    lateinit var temper: String
    lateinit var sharedPreferences : SharedPreferences
    lateinit var loading : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        country = view.findViewById(R.id.tv_country)
        loading = view.findViewById(R.id.progressBar)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        description = view.findViewById(R.id.tv_description)
        icon1 = view.findViewById(R.id.icon)
        temp = view.findViewById(R.id.tv_temp)
        d_t = view.findViewById(R.id.tv_date_time)
        recyclerView = view.findViewById(R.id.recycler)
        img_humidity = view.findViewById(R.id.img_humidity)
        desc_hum = view.findViewById(R.id.tv_deschum)
        img_pressure = view.findViewById(R.id.img_pressure)
        descpress = view.findViewById(R.id.tv_descpress)
        img_wind = view.findViewById(R.id.img_wind)
        desc_wind = view.findViewById(R.id.tv_descwind)
        img_cloud = view.findViewById(R.id.img_cloud)
        desc_cloud = view.findViewById(R.id.tv_desccloud)
        img_uvi = view.findViewById(R.id.img_uvi)
        desc_uvi = view.findViewById(R.id.tv_descuvi)
        img_vis = view.findViewById(R.id.img_visibility)
        desc_vis = view.findViewById(R.id.tv_Descvis)
        recyclervieweeks = view.findViewById(R.id.weeks)
        shared = requireActivity().getSharedPreferences("MapPreferences", Context.MODE_PRIVATE)
        dialogShared = requireActivity().getSharedPreferences("Dialog", Context.MODE_PRIVATE)
        langShared = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        settings = Settings1()
        Temp_shared = requireActivity().getSharedPreferences("temp", Context.MODE_PRIVATE)
        sharedPreferences = requireActivity().getSharedPreferences("api", Context.MODE_PRIVATE)

        requestNewLocationData()
        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance
                (
                WeatherClient.getInstance(), ConcreteLocalSource.getInstance(requireContext())
            )
        )
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        // homeViewModel.mydata.observe(requireActivity()) {
        lifecycleScope.launch {
            homeViewModel.mydata.collectLatest {
                when (it) {
                    is ApiState.loading -> {

                    }
                    is ApiState.Success -> {
                        loading.visibility = View.GONE
                        var simpleDate = SimpleDateFormat("dd/M/yyyy")
                        var currentDate = simpleDate.format(it.x.current.dt * 1000L)
                        Log.i("TAG", "onViewCreated: " + it)
                        val url =
                            "https://openweathermap.org/img/wn/${it.x.current.weather.get(0).icon}@2x.png"
                        country.text = it.x.timezone
                        description.text = it.x.current.weather.get(0).description
                        Glide.with(requireContext()).load(url).into(icon1)
                        temp.text = it.x.current.temp.toString()
                        d_t.text = currentDate.toString()
                        adapter = HoursAdapter(it.x.hourly, requireContext())
                        mylayoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        img_humidity.setImageResource(R.drawable.humidity)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = mylayoutManager
                        adapter.setList(it.x.hourly)
                        adapter.notifyDataSetChanged()
                        desc_hum.text = it.x.current.humidity.toString() + "%"
                        img_pressure.setImageResource(R.drawable.pressure)
                        descpress.text = it.x.current.pressure.toString() + " hpa"
                        img_wind.setImageResource(R.drawable.wind)
                        desc_wind.text = it.x.current.wind_speed.toString()
                        img_cloud.setImageResource(R.drawable.cloud)
                        desc_cloud.text = it.x.current.clouds.toString() + "%"
                        img_uvi.setImageResource(R.drawable.uvi)
                        desc_uvi.text = it.x.current.uvi.toString()
                        img_vis.setImageResource(R.drawable.visibility)
                        desc_vis.text = it.x.current.visibility.toString() + "m"
                        adapterweeks = WeeksAdapter(it.x.daily, requireContext())
                        mylayoutManagerweeks = LinearLayoutManager(requireContext())
                        recyclervieweeks.adapter = adapterweeks
                        recyclervieweeks.layoutManager = mylayoutManagerweeks
                        adapterweeks.setList(it.x.daily)
                        adapterweeks.notifyDataSetChanged()
                    }
                    else -> {

                        Toast.makeText(
                            requireContext(),
                            "check your connection",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

            }
        }
    }

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    private fun CheckPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return result
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            com.example.weatherapplication.Permission_ID
        )
    }

    private fun getLastLocation() {
        if (CheckPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                Toast.makeText(requireContext(), "Turn on the location please", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        myLocationRequest = LocationRequest()
        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        myLocationRequest.setInterval(0)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        myFusedLocationClient.requestLocationUpdates(
            myLocationRequest,
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    val myLastLocation: Location = p0!!.lastLocation
                    latitude = myLastLocation.latitude
                    longitude = myLastLocation.longitude
                    myFusedLocationClient.removeLocationUpdates(this)
                    key1 = dialogShared.getString("GPS", "")!!
                    key2 = dialogShared.getString("Maps", "")!!
                    lan = langShared.getString("language", "")!!
                    temper = Temp_shared.getString("temp", "")!!
                    if (key1 == "gps") {
                        homeViewModel.getData(longitude, latitude, temper, lan)
                    }
                    if (key2 == "maps") {
                        val lat = shared.getFloat("latitude", 0.0f)
                        val long = shared.getFloat("longitude", 0.0f)
                        homeViewModel.getData(lat.toDouble(), long.toDouble(), temper, lan)
                    }
                    if (lan != null) {
                        settings.setLocal(lan, requireContext())
                        Log.i("mizo", "onLocationResult: " + lan)
                    }
                    sharedPreferences.edit().apply{
                         putInt("longitude" , longitude.toInt())
                         putInt("latitude" , latitude.toInt())
                         putString("language " , lan )
                         putString("units" , temper)
                        apply()
                    }
                }


            },
            Looper.myLooper()
        )
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()

    }
}