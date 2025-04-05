package com.example.tracker.authorization.data.dto

import com.example.tracker.util.Resource
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("is_valid") val isValid: Boolean?,
    val success: Boolean
)
