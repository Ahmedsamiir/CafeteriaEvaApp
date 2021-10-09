package com.example.cafeteria.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.USER_DATA

import com.example.cafeteria.fragments.HomeFragment
import com.example.cafeteria.fragments.OfferFragment

import com.example.cafeteria.adapters.CategoryAdapter
import com.example.cafeteria.adapters.OffersAdapter
import com.example.cafeteria.adapters.RecommendedAdapter
import com.example.cafeteria.models.CategoryResponse
import com.example.cafeteria.models.ProductResponse
import com.example.cafeteria.models.UserResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.CategoryService
import com.example.cafeteria.services.ProductService
import com.example.cafeteria.services.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var recyclerviewCategoryList:RecyclerView? = null
    var recyclerviewRecommendedList:RecyclerView?= null

    var bottom_navigation : BottomNavigationView? = null

    var recycleViewoffers : RecyclerView? = null


     val homeFragment = HomeFragment()
    val offerFragment = OfferFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation = findViewById(R.id.bottom_navigation)
        recyclerviewCategoryList = findViewById(R.id.view1)
        recyclerviewRecommendedList = findViewById(R.id.view2)
        recycleViewoffers = findViewById(R.id.rv_offers)
        makeCurrentFragment(homeFragment)




        // recycle for category Items
        recycleViewCategory()
        // recycle for Recommended Items
        recycleViewRecommended()
        // toggle between bottom navbar Item
        onBottomNavSelected()
    }

    //For bottom navigation bar:
   private fun makeCurrentFragment(fragment: Fragment)=
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.fl_wrapper,fragment)
           commit()


       }

    private fun onBottomNavSelected(){
        bottom_navigation!!.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> {
                    makeCurrentFragment(homeFragment)
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)

                }
                R.id.icc_cart ->
                {
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.ic_offer -> {
                    makeCurrentFragment(offerFragment)
                    recycleViewOffers()

                }

                R.id.ic_logout ->{
                    SessionManager(this@MainActivity).deleteAccessToken()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
            true
        }
    }

    // to get offers response
    private fun recycleViewOffers() {
        recycleViewoffers?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false )
        val productService: ProductService = ApiClient(this@MainActivity).buildService(ProductService::class.java)
        val requestCall: Call<List<ProductResponse>> = productService.getProductsInOffer()
        requestCall.enqueue(object: Callback<List<ProductResponse>>{
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if(response.isSuccessful){

                    val adapter = OffersAdapter(this@MainActivity,offeredProducts = response.body()!!)
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
                    Toast.makeText(this@MainActivity, "ERROR!", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                //loginBtn!!.isActivated = true
                Toast.makeText(this@MainActivity, "ERROR!",Toast.LENGTH_LONG).show()
            }

        })


    }


// to get category response
    private fun recycleViewCategory() {
        recyclerviewCategoryList?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false )
        val categoryService: CategoryService = ApiClient(this@MainActivity).buildService(CategoryService::class.java)
        val requestCall: Call<List<CategoryResponse>> = categoryService.getCategories()
        requestCall.enqueue(object: Callback<List<CategoryResponse>>{
            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                if(response.isSuccessful){

                   val adapter = CategoryAdapter(this@MainActivity,categoryList = response.body()!!)
                    recyclerviewCategoryList!!.adapter = adapter
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
                    Toast.makeText(this@MainActivity, "ERROR!", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                //loginBtn!!.isActivated = true
                Toast.makeText(this@MainActivity, "ERROR!",Toast.LENGTH_LONG).show()
            }

        })


    }
    // to get category response
    private fun recycleViewRecommended() {
        recyclerviewRecommendedList?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false )
        val productService: ProductService = ApiClient(this@MainActivity).buildService(ProductService::class.java)
        val requestCall: Call<List<ProductResponse>> = productService.getAllProducts()
        requestCall.enqueue(object: Callback<List<ProductResponse>>{
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if(response.isSuccessful){
                    //val categoryList : List<CategoryResponse>= response.body()!!
                    val adapter = RecommendedAdapter(this@MainActivity,recommendedList = response.body()!!)
                    recyclerviewRecommendedList!!.adapter = adapter
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
                    Toast.makeText(this@MainActivity, "ERROR!", Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }

    /**
     * To go to login page with user response data:
     * */
    private fun goToLogin(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    /**
     * To go to Cart page with user response data:
     * */
    private fun goToCart(){
        val intent = Intent(this@MainActivity, CartActivity::class.java)
        startActivity(intent)
        finish()
    }



}