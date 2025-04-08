package com.example.tracker.authorization.domain.interactor

import com.example.tracker.authorization.data.AuthorizationInteractor
import com.example.tracker.authorization.data.dto.LoginRequest
import com.example.tracker.authorization.domain.model.Authorization
import com.example.tracker.authorization.domain.model.Login
import com.example.tracker.authorization.domain.model.Refresh
import com.example.tracker.authorization.domain.repository.AuthorizationRepository
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthorizationInteractorImpl(
    private val repository: AuthorizationRepository
) : AuthorizationInteractor {
    override suspend fun authorization(
        email: String,
        password: String
    ): Flow<Resource<Authorization>> = flow {
        repository.authorization(email, password).collect { result ->
            emit(result)
        }
    }

    override suspend fun refresh(refreshToken: String): Flow<Resource<Refresh>> = flow {
        repository.refresh(refreshToken).collect { result ->
            emit(result)
        }
    }

    override suspend fun login(accessToken: String): Flow<Resource<Login>> = flow {
        try {
            val request = LoginRequest(accessToken)
            repository.login(request.accessToken).collect { result ->
                emit(result)
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun getAccessToken(): String {
        return repository.getAccessToken()
    }

    override fun getRefreshToken(): String {
        return repository.getRefreshToken()
    }
}