package com.example.tracker.registration.domain.model

data class Registration(
    val user_id: Int,
    val access_token: String,
    val refresh_token: String
)
