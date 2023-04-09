package com.example.weatherapplication.alert.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.alert.TimeDate

class DateAdapter(private var date : List<TimeDate>,
                  private val listener : OnDelete, context: Context) :
    RecyclerView.Adapter<DateAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_adapter, parent, false)
        return DateAdapter.viewHolder(view)
    }
    override fun getItemCount(): Int {
        return date.size
    }

    override fun onBindViewHolder(holder: DateAdapter.viewHolder, position: Int) {
        var time = date[position]
        holder.time_from.text = time.timefrom
        holder.date_From.text = time.datefrom
        holder.delete.setOnClickListener {
            listener.delete(time)
        }
    }
    fun setList(date: List<TimeDate>){
        this.date = date

    }
    class viewHolder( private val itemview : View) : RecyclerView.ViewHolder(itemview) {
       val time_from = itemview.findViewById<TextView>(R.id.tv_time_from)
        val date_From = itemview.findViewById<TextView>(R.id.tv_date_from)
        val delete = itemview.findViewById<Button>(R.id.btn_Delete)

    }

}



