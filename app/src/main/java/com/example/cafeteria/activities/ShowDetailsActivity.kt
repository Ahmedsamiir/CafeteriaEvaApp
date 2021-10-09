package com.example.cafeteria.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.CATEGORY_DATA
import com.example.cafeteria.R
import com.example.cafeteria.adapters.RecommendedAdapter
import com.example.cafeteria.models.CategoryResponse
import com.example.cafeteria.models.ProductResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var currentCategory: CategoryResponse

    private lateinit var recommendedAdapter: RecommendedAdapter
    private lateinit var recommendedList: MutableList<ProductResponse>

    private lateinit var productAdapter: RecommendedAdapter
    private lateinit var productsList: MutableList<ProductResponse>

    var mealDetails : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_details)

        mealDetails = findViewById(R.id.rv_mealdetails_meals)
        //set up recycler view
        mealDetails!!.layoutManager = LinearLayoutManager(this@ShowDetailsActivity)
        loadCurrentCategory()
    }

    private fun loadCurrentCategory(){
        val bundle:Bundle? = intent.extras
        if(bundle?.containsKey(CATEGORY_DATA)!!){
            currentCategory = intent.extras?.get(CATEGORY_DATA) as CategoryResponse
        }
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
                    recommendedList = response.body()!!
                    recommendedAdapter = RecommendedAdapter(this@ShowDetailsActivity,recommendedList)
                    mealDetails!!.adapter = recommendedAdapter

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
