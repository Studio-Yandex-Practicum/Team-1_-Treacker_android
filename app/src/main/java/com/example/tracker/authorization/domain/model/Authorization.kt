package com.example.tracker.authorization.domain.model

import com.google.gson.annotations.SerializedName

data class Authorization(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)