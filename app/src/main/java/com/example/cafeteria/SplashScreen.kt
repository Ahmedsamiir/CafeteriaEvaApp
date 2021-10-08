package com.example.cafeteria

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val logo = findViewById<ImageView>(R.id.food_logo)
        val bottomTextView = findViewById<TextView>(R.id.bottom_txt)
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        logo.startAnimation(topAnimation)
        bottomTextView.startAnimation(bottomAnimation)

        val splashScreenTimeOut = 4000
        val homeIntent = Intent(this@SplashScreen, IntroActivity::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashScreenTimeOut.toLong())
    }
}