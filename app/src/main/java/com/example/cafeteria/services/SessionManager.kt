package com.example.cafeteria.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.cafeteria.models.UserResponse

import com.example.cafeteria.R
import com.example.cafeteria.models.RegisterResponse

import com.google.gson.Gson

/**
 * Session manager for storing and retrieving sessions from SharedPreferences
 */
class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    /**
     * save access_token for userLogin
     */
    fun saveAccessToken(userResponse: UserResponse) {
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(userResponse)
        editor.putString(ACCESS_TOKEN,json).apply()
        Log.d(ACCESS_TOKEN,"Access token saved successfully")
    }

    /**
     * save access_token for userRegister
     */
    fun saveAccessTokenRegister(registerResponse: RegisterResponse) {
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(registerResponse)
        editor.putString(ACCESS_TOKEN,json).apply()
        Log.d(ACCESS_TOKEN,"Access token saved successfully")
    }


    /**
     * get access_token for userLogin
     */
    fun fetchAccessToken(): UserResponse? {
        //prefs.getString(ACCESS_TOKEN, null)
        val gson = Gson()
        val json = prefs.getString(ACCESS_TOKEN, null)
        return gson.fromJson(json, UserResponse::class.java)
    }

    /**
     * get access_token for registerLogin
     */
    fun fetchAccessTokenRegister(): RegisterResponse? {
        //prefs.getString(ACCESS_TOKEN, null)
        val gson = Gson()
        val json = prefs.getString(ACCESS_TOKEN, null)
        return gson.fromJson(json, RegisterResponse::class.java)
    }

    /**
     * delete access_token / clear shared preferences
     */
    fun deleteAccessToken() {
        val editor = prefs.edit()
        editor.clear()
            .apply()
        Log.d(ACCESS_TOKEN,"Access token deleted successfully")
    }

}