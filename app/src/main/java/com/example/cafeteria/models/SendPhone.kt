package com.example.cafeteria.models

import java.io.Serializable


data class SendPhoneRequest(
    val phoneNumber: String
)

data class PhoneResponse(
    val email: String,
    val isSent: Boolean,
    val message: Any,
    val otpExpiresOn: String,
    val roles: List<String>,
    val username: String
):Serializable
