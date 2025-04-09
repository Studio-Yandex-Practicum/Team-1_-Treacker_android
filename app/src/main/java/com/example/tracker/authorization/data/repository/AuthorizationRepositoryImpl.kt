package com.example.tracker.authorization.data.repository

import android.content.SharedPreferences
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
import retrofit2.Response

class AuthorizationRepositoryImpl(
    private val networkClientAuthorization: NetworkClientAuthorization,
    private var sharedPreferences: SharedPreferences
) : AuthorizationRepository {
    override fun authorization(
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
            val editor = sharedPreferences.edit()

            if (response.isSuccessful) {
                val loginResponse = response.body()
                Log.d("123", "$loginResponse")
                if (loginResponse != null) {
                    val authorization = loginResponse.toAuthorization()
                    emit(Resource.Success(authorization))
                    editor.putString("access_token", authorization.accessToken)
                    editor.putString("refresh_token", authorization.refreshToken)
                    editor.putString("user_id", authorization.userId.toString())
                    editor.apply()
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

    override fun refresh(refreshToken: String): Flow<Resource<Refresh>> = flow {
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

    override fun login(accessToken: String): Flow<Resource<Login>> = flow {
        try {
            if (accessToken.isEmpty()) {
                emit(Resource.Error("Токен не может быть пустым"))
                return@flow
            }

            emit(handleLoginRequest(accessToken))

        } catch (e: Exception) {
            Log.e("Registration", "Ошибка при выполнении запроса: ${e.message}", e)
            emit(Resource.Error("Сетевая ошибка: ${e.message}"))
        }
    }

    private suspend fun handleLoginRequest(token: String): Resource<Login> {
        val request = LoginRequest(token)
        val response = networkClientAuthorization.login(request)

        return if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                Resource.Success(loginResponse.toLogin())
            } ?: Resource.Error("Пустое тело ответа")
        } else {
            handleLoginError(response)
        }
    }

    private suspend fun handleLoginError(response: Response<LoginResponse>): Resource<Login> {
        return when (response.code()) {
            401 -> {
                val refreshToken = sharedPreferences.getString("refresh_token", null)
                if (refreshToken != null) {
                    val newRequest = LoginRequest(refreshToken)
                    val newResponse = networkClientAuthorization.login(newRequest)
                    if (newResponse.isSuccessful) {
                        newResponse.body()?.let { newLoginResponse ->
                            Resource.Success(newLoginResponse.toLogin())
                        } ?: Resource.Error("Пустое тело ответа")
                    } else {
                        Resource.Error("Ошибка: ${newResponse.code()} - ${newResponse.message()}")
                    }
                } else {
                    Resource.Error("Не удалось обновить токен")
                }
            }
            else -> Resource.Error("Ошибка: ${response.code()} - ${response.message()}")
        }
    }

    override suspend fun getAccessToken(): String {
        val accessToken = sharedPreferences.getString("access_token", "") ?: ""
        return accessToken
    }

    override suspend fun getRefreshToken(): String {
        val refreshToken = sharedPreferences.getString("refresh_token", "") ?: ""
        return refreshToken
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