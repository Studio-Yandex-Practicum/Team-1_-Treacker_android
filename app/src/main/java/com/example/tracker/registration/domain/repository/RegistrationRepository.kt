package com.example.tracker.registration.domain.repository

import com.example.tracker.registration.domain.model.Registration
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun registration(email: String, password: String): Flow<Resource<Registration>>
    fun setAccessToken(accessToken: String)
    fun setRefreshToken(refreshToken: String)
    fun setIdToken(idToken: Int)
}