package com.example.weatherapplication.alert.view

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient
import kotlinx.coroutines.*

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        var repo =Repository.getInstance(WeatherClient.getInstance() , ConcreteLocalSource.getInstance(p0!!))
     val sharedPreferences =p0.getSharedPreferences("api" , Context.MODE_PRIVATE)
        val shared = p0.getSharedPreferences("notification_alarm" , Context.MODE_PRIVATE)
        val notification = shared.getString("notification" , "")
        val lat = sharedPreferences.getInt("latitude" , 0)
        val long = sharedPreferences.getInt("longitude" , 0)
        val units = sharedPreferences.getString("units" , "")
        val lang = sharedPreferences.getString("language" , "")
        CoroutineScope(Dispatchers.IO).launch {
            var data = repo.getData(lat.toDouble(), long.toDouble(), units!!, lang!!)
            if (notification == "Notification") {
                val intent = Intent(p0!!, MainActivity::class.java)
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(p0, 0, intent, 0)
                val builder = NotificationCompat.Builder(p0!!, "gedo")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(data.timezone)
                    .setContentText(data.current.temp.toString())
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                val notficationManager = NotificationManagerCompat.from(p0)
                if (ActivityCompat.checkSelfPermission(
                        p0,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                notficationManager.notify(15, builder.build())

            }
             if (notification == "Alarm"){
                setAlarm(p0,data.current.weather.get(0).description)
            }

        }

    }
    private suspend fun setAlarm(context: Context , description : String) {

        var flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE


        val mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)

        val view: View = LayoutInflater.from(context).inflate(R.layout.alarm_dialog, null, false)
        val dismissBtn = view.findViewById<TextView>(R.id.textView2)
        val textView = view.findViewById<TextView>(R.id.tv_desc)
        val layoutParams =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                flag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        layoutParams.gravity = Gravity.TOP

        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager

        withContext(Dispatchers.Main) {
            windowManager.addView(view, layoutParams)
            view.visibility = View.VISIBLE
            textView.text = description
        }

        mp.start()
        mp.isLooping = true
        dismissBtn.setOnClickListener {
            mp?.release()
            windowManager.removeView(view)
        }

    }
}