package com.example.weatherapplication.maps

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
import com.example.weatherapplication.R
import com.example.weatherapplication.dialog.Close

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment(var listener: Close) : Fragment()  {
    lateinit var search: EditText
    private var marker: Marker? = null
    private lateinit var map: GoogleMap
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var save: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        sharedPreferences =
            requireActivity().getSharedPreferences("MapPreferences", Context.MODE_PRIVATE)
        mapFragment?.getMapAsync(callback)
        search = view.findViewById(R.id.SearchEdt)
        save = view.findViewById(R.id.done)
        save.setOnClickListener {
            listener.close()
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
             val addresses: List<Address> = geocoder.getFromLocationName(searchedt, 1) as List<Address>
             if (addresses.isNotEmpty()) {
                 val address: Address = addresses[0]
                 val latitude: Double = address.latitude
                 val longitude: Double = address.longitude
                 gotoLangLan(latitude , longitude , 17f)


             } else {

             }
         } else {

         }


     }

     fun gotoLangLan(latitude: Double, longitude: Double, fl: Float) {
        var latLng = LatLng(latitude , longitude)
         val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, fl)
         val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync { googleMap ->
             googleMap.animateCamera(cameraUpdate)
         }
     }






        private val callback = OnMapReadyCallback { googleMap ->
            map = googleMap
            map.setOnMapClickListener { latLng ->
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                val countryName = addresses?.get(0)?.countryName
                val markerTitle = "Marker in $countryName"
                if (marker != null) {
                    marker?.remove()
                }
                marker = map.addMarker(MarkerOptions().position(latLng).title(markerTitle))
                with(sharedPreferences.edit()) {
                    putFloat("latitude", latLng.latitude.toFloat())
                    Log.i("TAG", ": " + latLng.latitude)
                    putFloat("longitude", latLng.longitude.toFloat())
                    apply()
                }
    }


    }
}