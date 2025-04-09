package com.example.tracker.registration.domain.repository

import com.example.tracker.registration.domain.model.Registration
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun registration(email: String, password: String): Flow<Resource<Registration>>
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun setIdToken(idToken: Int)
}