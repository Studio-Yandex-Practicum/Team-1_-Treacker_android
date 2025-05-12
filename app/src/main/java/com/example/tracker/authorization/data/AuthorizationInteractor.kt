package com.example.tracker.authorization.data

import com.example.tracker.authorization.domain.model.Authorization
import com.example.tracker.authorization.domain.model.Login
import com.example.tracker.authorization.domain.model.Refresh
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthorizationInteractor {
    fun authorization(email: String, password: String): Flow<Resource<Authorization>>
    fun refresh(refreshToken: String): Flow<Resource<Refresh>>
    fun login(accessToken: String): Flow<Resource<Login>>
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
}