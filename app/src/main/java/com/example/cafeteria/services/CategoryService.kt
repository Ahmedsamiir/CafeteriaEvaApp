package com.example.cafeteria.services

import com.example.cafeteria.models.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface CategoryService {

    /**Get all categories*/
    @GET("Category/getCategories")
    @Streaming
    fun getCategories() : Call<List<CategoryResponse>>

    /**Get single category by ID*/
    @GET("Category/getCategory")
    fun getSingleCategory(@Query("id") id:Int):Call<CategoryResponse>
}