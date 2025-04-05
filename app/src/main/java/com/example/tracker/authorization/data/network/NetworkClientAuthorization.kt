package com.example.tracker.authorization.data.network

import com.example.tracker.authorization.data.dto.AuthorizationRequest
import com.example.tracker.authorization.data.dto.AuthorizationResponse
import com.example.tracker.authorization.data.dto.LoginRequest
import com.example.tracker.authorization.data.dto.LoginResponse
import com.example.tracker.authorization.data.dto.RefreshRequest
import com.example.tracker.authorization.data.dto.RefreshResponse

interface NetworkClientAuthorization {
    suspend fun doRequest(request: AuthorizationRequest): retrofit2.Response<AuthorizationResponse>
    suspend fun refresh(refreshRequest: RefreshRequest): retrofit2.Response<RefreshResponse>
    suspend fun login(request: LoginRequest): retrofit2.Response<LoginResponse>


}