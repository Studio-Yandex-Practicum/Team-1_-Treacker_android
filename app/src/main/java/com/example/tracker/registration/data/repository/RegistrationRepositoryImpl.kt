package com.example.tracker.registration.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.tracker.registration.data.dto.RegistrationRequest
import com.example.tracker.registration.data.dto.RegistrationResponse
import com.example.tracker.registration.data.network.NetworkClient
import com.example.tracker.registration.domain.model.Registration
import com.example.tracker.registration.domain.repository.RegistrationRepository
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationRepositoryImpl(
    private val networkClient: NetworkClient,
    sharedPreferences: SharedPreferences
) : RegistrationRepository {
    val editor = sharedPreferences.edit()
    override suspend fun registration(
        email: String,
        password: String
    ): Flow<Resource<Registration>> = flow {
        try {
            if (email.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Email или пароль не могут быть пустыми"))
                return@flow
            }
            val request = RegistrationRequest(email, password)
            val response = networkClient.doRequest(request)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                Log.d("123", "$loginResponse")
                if (loginResponse != null) {
                    val registration = loginResponse.toRegistration()
                    emit(Resource.Success(registration))
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

    override fun setAccessToken(accessToken: String) {
        editor.putString("access_token", accessToken)
        editor.apply()
    }

    override fun setRefreshToken(refreshToken: String) {
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }

    override fun setIdToken(idToken: Int) {
        editor.putString("user_id", idToken.toString())
        editor.apply()
    }

    private fun RegistrationResponse.toRegistration(): Registration {
        return Registration(
            userId = this.userId ?: throw IllegalArgumentException("user_id не может быть null"),
            accessToken = this.accessToken
                ?: throw IllegalArgumentException("access_token не может быть null"),
            refreshToken = this.refreshToken
                ?: throw IllegalArgumentException("refresh_token не может быть null")
        )
    }
}

