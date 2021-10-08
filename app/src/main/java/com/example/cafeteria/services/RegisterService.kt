package com.example.cafeteria.services

import com.example.cafeteria.models.RegisterRequest
import com.example.cafeteria.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    companion object {
        const val TOKEN_ENDPOINT = "auth/register"
    }
    //Add New Destination
    //we will need body (object) to be added to destination list
    @POST(TOKEN_ENDPOINT)
    fun userRegister(@Body registerRequest: RegisterRequest) : Call<RegisterResponse>
}