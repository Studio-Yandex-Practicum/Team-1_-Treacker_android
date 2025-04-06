package com.example.tracker.authorization.data.repository

import android.util.Log
import com.example.tracker.authorization.data.dto.AuthorizationRequest
import com.example.tracker.authorization.data.dto.AuthorizationResponse
import com.example.tracker.authorization.data.dto.LoginRequest
import com.example.tracker.authorization.data.dto.LoginResponse
import com.example.tracker.authorization.data.dto.RefreshRequest
import com.example.tracker.authorization.data.dto.RefreshResponse
import com.example.tracker.authorization.data.network.NetworkClientAuthorization
import com.example.tracker.authorization.domain.model.Authorization
import com.example.tracker.authorization.domain.model.Login
import com.example.tracker.authorization.domain.model.Refresh
import com.example.tracker.authorization.domain.repository.AuthorizationRepository
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthorizationRepositoryImpl(
    private val networkClientAuthorization: NetworkClientAuthorization
) : AuthorizationRepository {
    override suspend fun authorization(
        email: String,
        password: String
    ): Flow<Resource<Authorization>> = flow {
        try {
            if (email.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Email или пароль не могут быть пустыми"))
                return@flow
            }
            val request = AuthorizationRequest(email, password)
            val response = networkClientAuthorization.doRequest(request)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                Log.d("123", "$loginResponse")
                if (loginResponse != null) {
                    val authorization = loginResponse.toAuthorization()
                    emit(Resource.Success(authorization))
                } else {
                    emit(Resource.Error("Пустое тело ответа"))
                }
            } else {
                emit(Resource.Error("Ошибка: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("Registration", "Ошибка при выполнении запроса: ${e.message}", e)
            emit(Resource.Error("Сетевая ошибка: ${e.message}"))
        }
    }

    override suspend fun refresh(refreshToken: String): Flow<Resource<Refresh>> = flow {
        try {
            if (refreshToken.isEmpty()) {
                emit(Resource.Error("Token обновления не может быть пустым"))
                return@flow
            }
            val request = RefreshRequest(refreshToken)
            val response = networkClientAuthorization.refresh(request)

            if (response.isSuccessful) {
                val refreshResponse = response.body()
                Log.d("123", "$refreshResponse")
                if (refreshResponse != null) {
                    val refresh = refreshResponse.toRefresh()
                    emit(Resource.Success(refresh))
                } else {
                    emit(Resource.Error("Пустое тело ответа"))
                }
            } else {
                emit(Resource.Error("Ошибка: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("Registration", "Ошибка при выполнении запроса: ${e.message}", e)
            emit(Resource.Error("Сетевая ошибка: ${e.message}"))
        }
    }

    override suspend fun login(accessToken: String): Flow<Resource<Login>> = flow {
        try {
            if (accessToken.isEmpty()) {
                emit(Resource.Error("Токен не может быть пустым"))
                return@flow
            }
            val request = LoginRequest(accessToken)
            val response = networkClientAuthorization.login(request)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                Log.d("123", "$loginResponse")
                if (loginResponse != null) {
                    val login = loginResponse.toLogin()
                    emit(Resource.Success(login))
                } else {
                    emit(Resource.Error("Пустое тело ответа"))
                }
            } else {
                emit(Resource.Error("Ошибка: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("Registration", "Ошибка при выполнении запроса: ${e.message}", e)
            emit(Resource.Error("Сетевая ошибка: ${e.message}"))
        }
    }

    private fun AuthorizationResponse.toAuthorization(): Authorization {
        return Authorization(
            userId = this.userId ?: throw IllegalArgumentException("user_id не может быть null"),
            accessToken = this.accessToken
                ?: throw IllegalArgumentException("access_token не может быть null"),
            refreshToken = this.refreshToken
                ?: throw IllegalArgumentException("refresh_token не может быть null")
        )
    }

    private fun RefreshResponse.toRefresh(): Refresh {
        return Refresh(
            accessToken = this.accessToken
                ?: throw IllegalArgumentException("access_token не может быть null"),
            refreshToken = this.refreshToken
                ?: throw IllegalArgumentException("refresh_token не может быть null")
        )
    }

    private fun LoginResponse.toLogin(): Login {
        return Login(
            isValid = this.isValid,
            success = this.success
        )
    }
}