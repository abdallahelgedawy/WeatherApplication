package com.example.weatherapplication.favorite.view

import android.content.Context
import android.location.Address
import android.location.Geocoder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.model.Current
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import java.io.IOException
import java.util.*

class FavoriteAdapter (private var data: List<Location>
    , private val Listener: Delete
    , context: Context
    ) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
        var context: Context = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fav, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
            var product : Location= data[position]
            var geoCoder = Geocoder(context, Locale.getDefault())
            var list :List<Address> = listOf()
            try {
                list = geoCoder.getFromLocation(product.lat,product.lon , 1) as List<Address>
            }catch(e: IOException){
                e.printStackTrace()
            }
            if (list.size >0) {
                var adress: Address = list.get(0)
                var k = adress.subAdminArea
                var y = adress.adminArea
                var x = adress.countryName
                holder.country.text = "$k,$y ,$x"
            }

            holder.delete.setOnClickListener {
                Listener.Remove(data.get(0))
            }

        }
            fun setList(data: List<Location>){
            this.data = data

        }
        class ViewHolder( private val itemview : View) : RecyclerView.ViewHolder(itemview) {
          val country : TextView = itemView.findViewById(R.id.tv_country)
            val delete :ImageButton = itemview.findViewById(R.id.img_delete)


        }

    }
