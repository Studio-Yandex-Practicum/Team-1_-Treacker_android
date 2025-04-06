package com.example.tracker.authorization.data

import com.example.tracker.authorization.domain.model.Authorization
import com.example.tracker.authorization.domain.model.Login
import com.example.tracker.authorization.domain.model.Refresh
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthorizationInteractor {
    suspend fun authorization(email: String, password: String): Flow<Resource<Authorization>>
    suspend fun refresh(refreshToken: String): Flow<Resource<Refresh>>
    suspend fun login(accessToken: String): Flow<Resource<Login>>
}