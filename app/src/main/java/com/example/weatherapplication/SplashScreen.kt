package com.example.weatherapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherapplication.dialog.Dialog

class SplashScreen : AppCompatActivity() {
  lateinit  var lottieAnimationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lottieAnimationView = findViewById(R.id.lottie)
        lottieAnimationView.animate().translationX(2000f).setDuration(2300)
            .setStartDelay(4000)
        Handler().postDelayed({
            var intent = Intent(this , Dialog::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}