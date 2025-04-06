package com.example.tracker.authorization.data.network

import com.example.tracker.authorization.data.dto.AuthorizationRequest
import com.example.tracker.authorization.data.dto.AuthorizationResponse
import com.example.tracker.authorization.data.dto.LoginRequest
import com.example.tracker.authorization.data.dto.LoginResponse
import com.example.tracker.authorization.data.dto.RefreshRequest
import com.example.tracker.authorization.data.dto.RefreshResponse
import retrofit2.Response

class RetrofitClientAuthorization(private val apiClientAuthorization: ApiClientAuthorization) :
    NetworkClientAuthorization {
    override suspend fun doRequest(
        request: AuthorizationRequest
    ): Response<AuthorizationResponse> {
        return apiClientAuthorization.authorization(request)
    }

    override suspend fun refresh(
        refreshRequest: RefreshRequest
    ): Response<RefreshResponse> {
        return apiClientAuthorization.refresh(refreshRequest)
    }

    override suspend fun login(
        request: LoginRequest
    ): Response<LoginResponse> {
        val bearerToken = "Bearer ${request.accessToken}"
        return apiClientAuthorization.login(bearerToken)
    }
}
