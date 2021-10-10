package com.example.cafeteria.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.adapters.OffersAdapter
import com.example.cafeteria.models.ProductResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ProductService
import com.example.cafeteria.services.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersActivity : AppCompatActivity() {
    var recycleViewoffers : RecyclerView? = null

    var bottom_navigation_offer : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)
        bottom_navigation_offer = findViewById(R.id.bottom_navigation_offer)
        recycleViewoffers = findViewById(R.id.rv_offers_offers)
        // set up list of offers
        recycleViewOffers()

        // toggle between bottom navbar Item
        onBottomNavSelected()
    }


    private fun recycleViewOffers() {
        recycleViewoffers?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false )
        val productService: ProductService = ApiClient(this@OffersActivity).buildService(
            ProductService::class.java)
        val requestCall: Call<List<ProductResponse>> = productService.getProductsInOffer()
        requestCall.enqueue(object: Callback<List<ProductResponse>> {
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if(response.isSuccessful){

                    val adapter = OffersAdapter(this@OffersActivity,offeredProducts = response.body()!!)
                    recycleViewoffers!!.adapter = adapter
                }else{
                    val errorCode:String = when(response.code()){
                        400 ->{
                            "Incorrect email or password."
                        }
                        404 -> {
                            "404 not found"
                        }
                        500 -> {
                            "500 server broken"
                        }
                        else ->{
                            "Unknown error!"
                        }
                    }
                    //loginBtn!!.isActivated = true
                    Toast.makeText(this@OffersActivity, "ERROR!", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                //loginBtn!!.isActivated = true
                Toast.makeText(this@OffersActivity, "ERROR!", Toast.LENGTH_LONG).show()
            }

        })


    }

    private fun onBottomNavSelected(){
        bottom_navigation_offer!!.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> {
                    val intent = Intent(this@OffersActivity, MainActivity::class.java)
                    startActivity(intent)

                }
                R.id.ic_cart ->
                {
                    val intent = Intent(this@OffersActivity, CartActivity::class.java)
                    startActivity(intent)
                }
                R.id.ic_offer -> {
                    //makeCurrentFragment(offerFragment)
                    //recycleViewOffers()
                    val intent = Intent(this@OffersActivity, OffersActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_logout ->{
                    SessionManager(this@OffersActivity).deleteAccessToken()
                    val intent = Intent(this@OffersActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
            true
        }
    }
}