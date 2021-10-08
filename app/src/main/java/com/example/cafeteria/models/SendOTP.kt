package com.example.cafeteria.models

import java.io.Serializable

data class SendOtpRequest(
    val otp: String,
    val phoneNumber: String
)

data class SendOtpResponse(
    val isCorrect: Boolean,
    val message: Any
):Serializable