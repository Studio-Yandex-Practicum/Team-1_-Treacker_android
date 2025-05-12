package com.example.tracker.registration.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.registration.data.RegistrationInteractor
import com.example.tracker.registration.domain.model.Registration
import com.example.tracker.util.RegistrationState
import com.example.tracker.util.Resource
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationInteractor: RegistrationInteractor
) : ViewModel() {
    private val state = MutableLiveData<RegistrationState>()
    fun getState(): LiveData<RegistrationState> = state

    fun loadData(email: String, password: String) {
        viewModelScope.launch {
            registrationInteractor
                .registration(email, password)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            processResult(resource.data)
                        }

                        is Resource.Error -> {
                            processError(resource.message.toString())
                        }
                    }
                }
        }
    }

    private fun processResult(data: Registration?) {
        if (data == null) {
            state.postValue(RegistrationState.Error("Получены пустые данные"))
        } else {
            state.postValue(RegistrationState.Content(data))
        }
    }

    private fun processError(message: String) {
        state.postValue(RegistrationState.Error(message))
    }

    suspend fun setAccessToken(accessToken: String) {
        registrationInteractor.setAccessToken(accessToken)
    }

    suspend fun setRefreshToken(refreshToken: String) {
        registrationInteractor.setRefreshToken(refreshToken)
    }

    suspend fun setIdToken(idToken: Int) {
        registrationInteractor.setIdToken(idToken)
    }
    fun fetchSetToken(accessToken: String, refreshToken: String, idToken: Int) {
        viewModelScope.launch {
            try {
                setAccessToken(accessToken)
                setRefreshToken(refreshToken)
                setIdToken(idToken)
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка при добавлении Sp ${e.message}")
            }
        }
    }
}