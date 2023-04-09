package com.example.weatherapplication.alert.view
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.alert.viewmodel.AlertViewModel
import com.example.weatherapplication.alert.viewmodel.AlertViewModelFactory
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Alert : Fragment() , OnDelete{
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    lateinit var add : FloatingActionButton
    val args : AlertArgs by navArgs()
     var timefrom : String =""
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DateAdapter
    lateinit var mylayoutManager: LinearLayoutManager
    lateinit var alertViewModelFactory : AlertViewModelFactory
    lateinit var alertViewModel : AlertViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Alert().apply {
                arguments = Bundle().apply {

                }
            }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "My App"
            val description ="Weather app"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("gedo" , name , importance)
            channel.description = description
            val notificationManager = activity?.getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }

    }

    private fun setAlarm() {
        alarmManager = activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext() , AlarmReceiver::class.java)
        Log.i("TAG", "setAlarm: "+timefrom)
        pendingIntent = PendingIntent.getBroadcast(requireContext() , 0 , intent , 0)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timefrom.toLong(),
            pendingIntent
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycle_Date)
        recyclerView.visibility = View.GONE
        add = view.findViewById(R.id.add_alert)
        add.setOnClickListener {
            if (!Settings.canDrawOverlays(requireContext())){
                requestOverAppPermission()
            }
            view.findNavController().navigate(R.id.alert_date)

        }

       if (args.timedate!=null){
         //  Log.i("TAG", "onViewCreated: "+args.time.timefrom.toString())
            timefrom =  args.timedate!!.timefrom
           Log.i("TAG", "onViewCreated: " +timefrom)
            setAlarm()
            createNotificationChannel()
        }


    }
    private fun requestOverAppPermission() {
        startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION),20)
    }

    override fun onResume() {
        super.onResume()
         alertViewModelFactory = AlertViewModelFactory(Repository.getInstance(WeatherClient.getInstance() , ConcreteLocalSource.getInstance(requireContext())))
         alertViewModel = ViewModelProvider(this , alertViewModelFactory).get(
            AlertViewModel::class.java)
        alertViewModel.finaldateTime.observe(requireActivity()){
            adapter = DateAdapter(it , this , requireContext())
            mylayoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = mylayoutManager
            recyclerView.visibility = View.VISIBLE
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun delete(date: TimeDate) {
        alertViewModel.delete(date)
        adapter.notifyDataSetChanged()
    }
}