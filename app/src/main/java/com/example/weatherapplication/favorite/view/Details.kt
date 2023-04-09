package com.example.weatherapplication.favorite.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.home.view.HoursAdapter
import com.example.weatherapplication.home.view.WeeksAdapter
import com.example.weatherapplication.home.viewModel.HomeViewModel
import com.example.weatherapplication.home.viewModel.HomeViewModelFactory
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat


class Details : Fragment() {
    private lateinit var myLocationRequest: LocationRequest
    lateinit var country : TextView
    lateinit var description : TextView
    lateinit var icon1 : ImageView
    lateinit var temp : TextView
    lateinit var d_t : TextView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HoursAdapter
    lateinit var mylayoutManager: LinearLayoutManager
    lateinit var img_humidity : ImageView
    lateinit var desc_hum : TextView
    lateinit var img_pressure : ImageView
    lateinit var descpress : TextView
    lateinit var img_wind : ImageView
    lateinit var desc_wind : TextView
    lateinit var img_cloud : ImageView
    lateinit var desc_cloud : TextView
    lateinit var img_uvi : ImageView
    lateinit var desc_uvi : TextView
    lateinit var img_vis : ImageView
    lateinit var desc_vis : TextView
    lateinit var recyclervieweeks: RecyclerView
    lateinit var adapterweeks: WeeksAdapter
    lateinit var mylayoutManagerweeks: LinearLayoutManager
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory

    var lon : Double = 0.0
    var lat : Double = 0.0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        country = view.findViewById(R.id.tv_country)
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
        if (savedInstanceState != null) {
            lon = savedInstanceState.getDouble("longitude")
            lat = savedInstanceState.getDouble("latitude")
        }
        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance
                (
                WeatherClient.getInstance(), ConcreteLocalSource.getInstance(requireContext())
            )
        )


        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getData(lon , lat , "metric", "ar")
        homeViewModel.mydata.observe(requireActivity()) {
            var simpleDate = SimpleDateFormat("dd/M/yyyy")
            var currentDate = simpleDate.format(it.current.dt * 1000L)
            val url = "https://openweathermap.org/img/wn/${it.current.weather.get(0).icon}@2x.png"
            country.text = it.timezone
            description.text = it.current.weather.get(0).description
            Glide.with(requireContext()).load(url).into(icon1)
            temp.text = it.current.temp.toString()
            d_t.text = currentDate.toString()
            adapter = HoursAdapter(it.hourly, requireContext())
            mylayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            img_humidity.setImageResource(R.drawable.humidity)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = mylayoutManager
            adapter.setList(it.hourly)
            adapter.notifyDataSetChanged()
            desc_hum.text = it.current.humidity.toString() + "%"
            img_pressure.setImageResource(R.drawable.pressure)
            descpress.text = it.current.pressure.toString() + " hpa"
            img_wind.setImageResource(R.drawable.wind)
            desc_wind.text = it.current.wind_speed.toString() + "m/s"
            img_cloud.setImageResource(R.drawable.cloud)
            desc_cloud.text = it.current.clouds.toString() + "%"
            img_uvi.setImageResource(R.drawable.uvi)
            desc_uvi.text = it.current.uvi.toString()
            img_vis.setImageResource(R.drawable.visibility)
            desc_vis.text = it.current.visibility.toString() + "m"
            adapterweeks = WeeksAdapter(it.daily, requireContext())
            mylayoutManagerweeks = LinearLayoutManager(requireContext())
            recyclervieweeks.adapter = adapterweeks
            recyclervieweeks.layoutManager = mylayoutManagerweeks
            adapterweeks.setList(it.daily)
            adapterweeks.notifyDataSetChanged()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             lon =it.getDouble("longitude")
             lat = it.getDouble("latitude")
            Log.i("TAG", "onCreate: " + lon)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Details().apply {
                arguments = Bundle().apply {

                }
            }
    }
}