package com.example.cafeteria.services

import com.example.cafeteria.models.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    /**
     * Get All products
     */
    @GET("Product/getAllProducts")
    fun getAllProducts() : Call<List<ProductResponse>>


    /**
     * Get all products which are in offer
     * */
    @GET("Product/getOffers")
    fun getProductsInOffer() : Call<List<ProductResponse>>

    /**
     * Get all products using category id
     * @param categoryId ex: currentCategory.id
     * */
    @GET("Product/getProducts")
    fun getCurrentCatProducts(@Query("categoryId") categoryId:String) : Call<MutableList<ProductResponse>>

    /**Get single product by ID*/
    @GET("Product/getProduct/{id}")
    fun getSingleProduct(@Path("id") id:Int): Call<ProductResponse>
}