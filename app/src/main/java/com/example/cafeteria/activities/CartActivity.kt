package com.example.cafeteria.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeteria.R
import com.example.cafeteria.services.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity:AppCompatActivity() {

    private var bottom_navigation_cart : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        bottom_navigation_cart = findViewById(R.id.bottom_navigation_cart)

        //toggle between pages
        onBottomNavSelected()


    }

    private fun onBottomNavSelected(){
        bottom_navigation_cart!!.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> {
                    val intent = Intent(this@CartActivity, MainActivity::class.java)
                    startActivity(intent)

                }
                R.id.ic_offer -> {

                    val intent = Intent(this@CartActivity, OffersActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_logout ->{
                    SessionManager(this@CartActivity).deleteAccessToken()
                    val intent = Intent(this@CartActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
            true
        }
    }
}