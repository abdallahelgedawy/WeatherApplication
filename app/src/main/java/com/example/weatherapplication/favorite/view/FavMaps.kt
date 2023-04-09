package com.example.weatherapplication.favorite.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.favorite.viewModel.FavoriteViewModel
import com.example.weatherapplication.favorite.viewModel.FavoriteViewModelFactory
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class FavMaps : Fragment() {
    lateinit var search: EditText
    private var marker: Marker? = null
    private lateinit var map: GoogleMap
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var fav: ImageButton
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
     var addresses: MutableList<Address>? = null
    var flag = false

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.setOnMapClickListener { latLng ->
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
             addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val countryName = addresses?.get(0)?.countryName
            val markerTitle = "Marker in $countryName"
            if (marker != null) {
                marker?.remove()
            }
            marker = map.addMarker(MarkerOptions().position(latLng).title(markerTitle))


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_maps, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.favmap) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        search = view.findViewById(R.id.SearchEdt)
        sharedPreferences =
            requireActivity().getSharedPreferences("FavPreferences", Context.MODE_PRIVATE)
        fav = view.findViewById(R.id.done)
        favoriteViewModelFactory = FavoriteViewModelFactory(
            Repository.getInstance(
                WeatherClient.getInstance(),
                ConcreteLocalSource.getInstance(requireContext())
            )
        )
       var langShared = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
       var Temp_shared = requireActivity().getSharedPreferences("temp", Context.MODE_PRIVATE)
       var lan = langShared.getString("language", "")!!
        var temper = Temp_shared.getString("temp", "")!!
        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
        fav.setOnClickListener {
            view.findNavController().navigate(R.id.maps_fav)
            favoriteViewModel.addToFavorite(addresses!!.get(0).longitude ,addresses!!.get(0).longitude , lan , temper)
                }

        search.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                goToSearchLocation()
                true
            } else {
                false
            }
        }
    }

    fun goToSearchLocation() {
        val searchedt: String = search.text.toString().trim()
        if (searchedt.isNotEmpty()) {
            val geocoder = Geocoder(requireContext())
            val addresses: List<Address> =
                geocoder.getFromLocationName(searchedt, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val latitude: Double = address.latitude
                val longitude: Double = address.longitude
                gotoLangLan(latitude, longitude, 17f)


            } else {

            }
        } else {

        }


    }

    fun gotoLangLan(latitude: Double, longitude: Double, fl: Float) {
        var latLng = LatLng(latitude, longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, fl)
        val mapFragment = childFragmentManager.findFragmentById(R.id.favmap) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.animateCamera(cameraUpdate)
        }
    }
}




