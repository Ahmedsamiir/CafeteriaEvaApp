package com.example.cafeteria.models

import java.io.Serializable

data class ResetPasswordRequest(
    val newPassword: String,
    val phoneNumber: String
)

data class ResetPasswordResponse(
    val email: String,
    val isChanged: Boolean,
    val message: Any,
    val username: String
):Serializable