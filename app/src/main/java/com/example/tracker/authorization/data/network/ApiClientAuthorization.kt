package com.example.tracker.authorization.data.network

import com.example.tracker.authorization.data.dto.AuthorizationRequest
import com.example.tracker.authorization.data.dto.AuthorizationResponse
import com.example.tracker.authorization.data.dto.LoginResponse
import com.example.tracker.authorization.data.dto.RefreshRequest
import com.example.tracker.authorization.data.dto.RefreshResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiClientAuthorization {
    @POST("/auth/login")
    suspend fun authorization(
        @Body authorizationRequest: AuthorizationRequest
    ): retrofit2.Response<AuthorizationResponse>

    @POST("/auth/refresh")
    suspend fun refresh(
        @Body refreshRequest: RefreshRequest
    ): retrofit2.Response<RefreshResponse>

    @GET("/auth/check")
    suspend fun login(
        @Header("Authorization") token: String
    ): retrofit2.Response<LoginResponse>
}