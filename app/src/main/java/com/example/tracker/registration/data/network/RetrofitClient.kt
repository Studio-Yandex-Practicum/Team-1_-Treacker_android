package com.example.tracker.registration.data.network

import com.example.tracker.registration.data.dto.RegistrationRequest
import com.example.tracker.registration.data.dto.RegistrationResponse

class RetrofitClient(private val apiClient: ApiClient) : NetworkClient {
    override suspend fun doRequest(request: RegistrationRequest): retrofit2.Response<RegistrationResponse> {
        return apiClient.registration(request)
    }
}