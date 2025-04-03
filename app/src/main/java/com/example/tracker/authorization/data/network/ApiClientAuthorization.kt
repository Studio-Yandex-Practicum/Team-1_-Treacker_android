package com.example.tracker.authorization.data.network

import com.example.tracker.authorization.data.dto.AuthorizationRequest
import com.example.tracker.authorization.data.dto.AuthorizationResponse
import com.example.tracker.authorization.data.dto.LoginRequest
import com.example.tracker.authorization.data.dto.LoginResponse
import com.example.tracker.registration.data.dto.RegistrationRequest
import com.example.tracker.registration.data.dto.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClientAuthorization {
    @POST("/auth/login")
    suspend fun authorization(@Body authorizationRequest: AuthorizationRequest): retrofit2.Response<AuthorizationResponse>
    @GET("/auth/check")
    suspend fun login(@Body loginRequest: LoginRequest): retrofit2.Response<LoginResponse>
}