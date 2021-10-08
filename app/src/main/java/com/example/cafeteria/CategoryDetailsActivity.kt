package com.example.cafeteria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeteria.models.CategoryResponse

class CategoryDetailsActivity : AppCompatActivity() {
    private lateinit var currentCategory: CategoryResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_details)
        loadCurrentCategory()
    }

    private fun loadCurrentCategory(){
        ///extract bundle
        //currentCategory = val from bundle
    }
}