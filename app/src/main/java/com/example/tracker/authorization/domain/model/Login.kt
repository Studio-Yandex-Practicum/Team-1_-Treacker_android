package com.example.tracker.authorization.domain.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("is_valid") val isValid: Boolean?
)
