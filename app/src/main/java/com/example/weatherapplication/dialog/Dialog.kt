package com.example.weatherapplication.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.maps.MapsFragment

class Dialog : AppCompatActivity() , Close {
    lateinit var dialog: AlertDialog
    lateinit var fragment: MapsFragment
    lateinit var sharedPreferences : SharedPreferences

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog2)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Choose the suitable choice ")
        sharedPreferences =
            getSharedPreferences("Dialog", Context.MODE_PRIVATE)

        builder.setPositiveButton("Use the default location") { dialog, which ->
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            with(sharedPreferences.edit()) {
                putString("GPS", "gps")
                apply()
            }
        }
        builder.setNegativeButton("Go to Maps") { dialog, which ->
            fragment = MapsFragment(this)
            supportFragmentManager.beginTransaction()
                .replace(R.id.map, fragment)
                .commit()
            with(sharedPreferences.edit()) {
                putString("Maps", "maps")
                apply()
            }

        }
        dialog = builder.create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences_maps = getSharedPreferences("Maps", Context.MODE_PRIVATE)
        if (sharedPreferences_maps.getBoolean("Maps", true) == false) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            with(sharedPreferences.edit()) {
                putString("GPS", "gps")
                apply()
            }
        }
        if (sharedPreferences_maps.getBoolean("Maps", false) == true) {
            fragment = MapsFragment(this)
            supportFragmentManager.beginTransaction()
                .replace(R.id.map, fragment)
                .commit()
            with(sharedPreferences.edit()) {
                putString("Maps", "maps")
                apply()
                dialog.cancel()
            }
        }
    }






    override fun close() {
        dialog.cancel()
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        startActivity(Intent(this , MainActivity::class.java))

    }

}
