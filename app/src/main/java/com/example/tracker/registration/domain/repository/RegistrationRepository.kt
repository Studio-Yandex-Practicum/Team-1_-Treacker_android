package com.example.tracker.registration.domain.repository

import com.example.tracker.util.Resource

import com.example.tracker.registration.domain.model.Registration
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun registration(email: String, password: String): Flow<Resource<Registration>>
}