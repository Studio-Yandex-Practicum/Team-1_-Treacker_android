package com.example.tracker.registration.data.network

import com.example.tracker.registration.data.dto.RegistrationRequest
import com.example.tracker.registration.data.dto.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("/auth/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): retrofit2.Response<RegistrationResponse>
}