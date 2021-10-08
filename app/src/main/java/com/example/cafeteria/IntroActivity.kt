package com.example.cafeteria

import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cafeteria.services.SessionManager

class IntroActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val startBtn = findViewById<ConstraintLayout>(R.id.startbtn)

        startBtn.setOnClickListener {
            if(SessionManager(this@IntroActivity).fetchAccessToken()!=null){
                val intent= Intent(this@IntroActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent= Intent(this@IntroActivity, LoginActivity::class.java)
                startActivity(intent)
            }


        }
    }
}