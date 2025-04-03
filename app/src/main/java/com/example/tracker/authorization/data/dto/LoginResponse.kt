package com.example.tracker.authorization.data.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("is_valid") val isValid: Boolean?
)
