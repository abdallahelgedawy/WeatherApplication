package com.example.weatherapplication.settings

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.weatherapplication.MainActivity

import com.example.weatherapplication.R
import com.example.weatherapplication.dialog.Close
import com.example.weatherapplication.dialog.Dialog
import com.example.weatherapplication.maps.MapsFragment
import java.util.Locale
import kotlin.math.log

class Settings1 : Fragment() , Close {


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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup_loc)
        val radioGroup_lang = view.findViewById<RadioGroup>(R.id.radioGroup2_lang)
        val radioGroup_temp = view.findViewById<RadioGroup>(R.id.radiogrp_temp)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = radioButton.text.toString()
            Toast.makeText(requireContext(), "$selectedText", Toast.LENGTH_SHORT).show()

            if (selectedText == "Gps") {
                view.findNavController().navigate(R.id.settings_main)
                val sharedPreferences =
                    requireActivity().getSharedPreferences("Maps", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("Maps", false)
                    apply()
                }
            }
            if (selectedText == "Maps") {
                view.findNavController().navigate(R.id.settings_dialog)
                val sharedPreferences =
                    requireActivity().getSharedPreferences("Maps", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("Maps", true)
                    apply()
                }
            }
        }

        radioGroup_lang.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = radioButton.text.toString()
            Toast.makeText(requireContext(), "$selectedText", Toast.LENGTH_SHORT).show()
            val sharedPreferences =
                requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                if (selectedText == "English" || selectedText == "انجليزي") {
                    setLocal("en",requireContext())
                    putString("language", "en")
                    apply()
                    Log.i(TAG, "onViewCreated: ")
                }
                     if (selectedText == "Arabic") {
                        setLocal("ar" , requireContext())
                        putString("language", "ar")
                        apply()
                    }
                activity?.startActivity(Intent(requireContext(),MainActivity::class.java))
                activity?.finish()

            }
        }
            radioGroup_temp.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = view.findViewById<RadioButton>(checkedId)
                val selectedText = radioButton.text.toString()
                Toast.makeText(requireContext(), "$selectedText", Toast.LENGTH_SHORT).show()
                val sharedPreferences =
                    requireActivity().getSharedPreferences("temp", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    if (selectedText == "Standard") {
                        putString("temp", "standard")
                        apply()
                    }
                    if (selectedText == "Metric"){
                        putString("temp" , "metric")
                        apply()
                    }
                    if (selectedText == "Imprial"){
                        putString("temp" , "imperial")
                        apply()
                    }
                }
            }

    }

    fun setLocal(lang : String , context: Context) {
       var local = Locale(lang)
        Locale.setDefault(local)
        var config = Configuration()
        config.locale = local
        context?.resources?.updateConfiguration(config,context?.resources?.displayMetrics)
    }



    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings1().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun close() {
    }
}