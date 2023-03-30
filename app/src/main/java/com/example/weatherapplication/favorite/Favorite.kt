package com.example.weatherapplication.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapplication.R
import com.example.weatherapplication.dialog.Close
import com.google.android.material.floatingactionbutton.FloatingActionButton


class  Favorite : Fragment() , Close {
  lateinit var fav : FloatingActionButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fav = view.findViewById(R.id.btn_fav)
        fav.setOnClickListener {

        }

    }

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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favorite().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun close() {

    }
}