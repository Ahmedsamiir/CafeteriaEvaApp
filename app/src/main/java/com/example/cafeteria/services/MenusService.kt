package com.example.cafeteria.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MenusService {
    /**
     * We can call all menus from mockAPI
     * till get original API
     * */
    @GET
    fun getAllMenus(@Url serverUrl:String): Call<String>

}