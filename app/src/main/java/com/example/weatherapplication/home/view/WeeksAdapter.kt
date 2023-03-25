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
import com.example.weatherapplication.model.Daily
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class WeeksAdapter(private var data: List<Daily>, context : Context) : RecyclerView.Adapter<WeeksAdapter.ViewHolder>() {
        var context: Context = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeksAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.weeks, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: WeeksAdapter.ViewHolder, position: Int) {
            var mydata: Daily = data[position]
            val url = "https://openweathermap.org/img/wn/${mydata.weather.get(0).icon}@2x.png"
            var date = Date(mydata.dt*1000L)
            var sdf = SimpleDateFormat("d")
            sdf.timeZone = TimeZone.getDefault()
            var formattedDate = sdf.format(date)
            var intDay = formattedDate.toInt()

            var calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.DAY_OF_MONTH , intDay)

            var format = SimpleDateFormat("EEEE")
            var day = format.format(calendar.time)
            var max = Math.ceil(mydata.temp.max).toInt()
            var min = Math.ceil(mydata.temp.min).toInt()
            holder.weeks.text = day
            Glide.with(context).load(url).into(holder.img)
            holder.desc.text = mydata.weather.get(0).description
            holder.temp.text = "$max/$min C"
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setList( product : List<Daily>){
            this.data = product
        }
        class ViewHolder( private val itemview : View) : RecyclerView.ViewHolder(itemview) {
           val weeks : TextView = itemView.findViewById(R.id.tv_weeks)
            val img : ImageView = itemview.findViewById(R.id.img_weeks)
            val desc : TextView = itemView.findViewById(R.id.tv_descweeks)
            val temp : TextView = itemView.findViewById(R.id.tv_tempweeks)
        }

    }

