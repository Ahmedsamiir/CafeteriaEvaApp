package com.example.cafeteria.services

import com.example.cafeteria.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ResetPasswordService {
    @POST("auth/resendOTP")
    fun sendPhoneNumber(@Body sendPhoneRequest: SendPhoneRequest) : Call<PhoneResponse>

    @POST("auth/verifyOTP")
    fun sendOtp(@Body sendOtpRequest: SendOtpRequest):Call<SendOtpResponse>

    @POST("auth/resetPassword")
    fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest):Call<ResetPasswordResponse>

}