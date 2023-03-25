package com.example.weatherapplication.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.model.Current
import java.text.SimpleDateFormat
import java.util.*


class HoursAdapter
(private var data: List<Current>, context : Context
    ) : RecyclerView.Adapter<HoursAdapter.ViewHolder>() {
        var context: Context = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.hours, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: HoursAdapter.ViewHolder, position: Int) {
            var mydata: Current = data[position]
            val url = "https://openweathermap.org/img/wn/${mydata.weather.get(0).icon}@2x.png"
            var hrs = Date(mydata.dt*1000)
            var sdf = SimpleDateFormat("hh:mm a")
            sdf.timeZone = TimeZone.getDefault()
            var formatedData = sdf.format(hrs)
            Glide.with(context).load(url).into(holder.icon)
            holder.time.text = formatedData.toString()
            holder.desc.text = mydata.weather.get(0).description
            holder.temp.text = mydata.temp.toString()
            holder.wind.setImageResource(R.drawable.wind)
            holder.img_dew.setImageResource(R.drawable.dew)
            holder.tv_wind.text = mydata.wind_speed.toString()+"Km/h"
            holder.dew.text = mydata.dew_point.toString() + "%"
        }

    override fun getItemCount(): Int {
         return data.size
    }

    fun setList( product : List<Current>){
            this.data = product
        }
        class ViewHolder( private val itemview : View) : RecyclerView.ViewHolder(itemview) {
          val time : TextView = itemView.findViewById(R.id.tv_hour)
            val icon : ImageView = itemview.findViewById(R.id.img_weather)
            val desc : TextView = itemView.findViewById(R.id.tv_desc)
            val temp : TextView = itemView.findViewById(R.id.tv_temperature)
            val wind : ImageView = itemview.findViewById(R.id.img_wind)
            val tv_wind : TextView = itemView.findViewById(R.id.tv_wind)
            val img_dew : ImageView = itemview.findViewById(R.id.img_dew)
            val dew : TextView = itemView.findViewById(R.id.tv_dew)


        }

    }
