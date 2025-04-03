package com.example.tracker.authorization.data.dto

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?
)
