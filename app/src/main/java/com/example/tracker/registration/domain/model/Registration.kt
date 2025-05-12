package com.example.tracker.registration.domain.model

import com.google.gson.annotations.SerializedName

data class Registration(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)
