package com.example.cafeteria.services

import com.example.cafeteria.models.UserRequest
import com.example.cafeteria.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    companion object {
        const val TOKEN_ENDPOINT = "auth/login"
    }

    //Add new destiny
    //we will need body (object) to be added to destination list
    @POST(TOKEN_ENDPOINT)
    fun userLogin(@Body userRequest: UserRequest) : Call<UserResponse>
}