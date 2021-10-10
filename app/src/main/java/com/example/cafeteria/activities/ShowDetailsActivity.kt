package com.example.cafeteria.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.CATEGORY_DATA
import com.example.cafeteria.R
import com.example.cafeteria.adapters.MealAdapter
import com.example.cafeteria.adapters.RecommendedAdapter
import com.example.cafeteria.models.CategoryResponse
import com.example.cafeteria.models.ProductResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ProductService
import com.example.cafeteria.services.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var currentCategory: CategoryResponse
        // declare recommended list of meals

    // declare meals inside category
    private lateinit var mealAdapter : MealAdapter
    private lateinit var mealList: MutableList<ProductResponse>
    // declare bottom navbar for category
    private var bottom_navigation_category: BottomNavigationView? = null

    var mealDetails : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_details)

        bottom_navigation_category = findViewById(R.id.bottom_navigation_category)
        mealDetails = findViewById(R.id.rv_mealdetails_meals)
        //set up recycler view
        mealDetails!!.layoutManager = LinearLayoutManager(this@ShowDetailsActivity)
        loadCurrentCategory()

        // toggle between screen
        onBottomNavSelected()
    }

    private fun onBottomNavSelected(){
        bottom_navigation_category!!.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> {
                    val intent = Intent(this@ShowDetailsActivity, MainActivity::class.java)
                    startActivity(intent)

                }
                R.id.ic_cart ->
                {
                    val intent = Intent(this@ShowDetailsActivity, CartActivity::class.java)
                    startActivity(intent)
                }
                R.id.ic_offer -> {
                    //makeCurrentFragment(offerFragment)
                    //recycleViewOffers()
                    val intent = Intent(this@ShowDetailsActivity, OffersActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_logout ->{
                    SessionManager(this@ShowDetailsActivity).deleteAccessToken()
                    val intent = Intent(this@ShowDetailsActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
            true
        }
    }

    // receive data from last activity(Category in main screen)
    private fun loadCurrentCategory(){
        val bundle:Bundle? = intent.extras
        if(bundle?.containsKey(CATEGORY_DATA)!!){
            currentCategory = intent.extras?.get(CATEGORY_DATA) as CategoryResponse
        }
        loadCategoryProducts()
    }

    /**
     * To load all category products from API
     * */
    private fun loadCategoryProducts() {

        //Connect to db send current Category ID and get response List<ProductsResponse>
        val productService: ProductService = ApiClient(this@ShowDetailsActivity).buildService(ProductService::class.java)
        val requestCall: Call<MutableList<ProductResponse>> = productService.getCurrentCatProducts(currentCategory.id.toString())
        requestCall.enqueue(object: Callback<MutableList<ProductResponse>> {
            override fun onResponse(call: Call<MutableList<ProductResponse>>, response: Response<MutableList<ProductResponse>>) {
                if(response.isSuccessful){
                    mealList = response.body()!!
                    mealAdapter = MealAdapter(this@ShowDetailsActivity,mealList)
                    mealDetails!!.adapter = mealAdapter

                }else{

                    val errorCode:String = when(response.code()){
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
                }
            }

            override fun onFailure(call: Call<MutableList<ProductResponse>>, t: Throwable) {
               Toast.makeText(this@ShowDetailsActivity, "ERROR!", Toast.LENGTH_LONG).show()
            }

        })

    }


    }




