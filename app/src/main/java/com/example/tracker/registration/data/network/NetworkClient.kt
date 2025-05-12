package com.example.tracker.registration.data.network

import com.example.tracker.registration.data.dto.RegistrationRequest
import com.example.tracker.registration.data.dto.RegistrationResponse

interface NetworkClient {
    suspend fun doRequest(request: RegistrationRequest): retrofit2.Response<RegistrationResponse>
}