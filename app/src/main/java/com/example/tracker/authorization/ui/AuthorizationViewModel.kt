package com.example.tracker.authorization.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.authorization.data.AuthorizationInteractor
import com.example.tracker.authorization.domain.model.Authorization
import com.example.tracker.authorization.domain.model.Login
import com.example.tracker.util.AuthorizationState
import com.example.tracker.util.LoginState
import com.example.tracker.util.Resource
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val authorizationInteractor: AuthorizationInteractor
) : ViewModel() {
    private val state = MutableLiveData<AuthorizationState>()
    fun getState(): LiveData<AuthorizationState> = state

    private val loginState = MutableLiveData<LoginState>()
    fun getLoginState(): LiveData<LoginState> = loginState

    fun loadData(email: String, password: String) {
        viewModelScope.launch {
            authorizationInteractor
                .authorization(email, password)
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
    private fun processResult(data: Authorization?) {
        if (data == null) {
            state.postValue(AuthorizationState.Error("Получены пустые данные"))
        } else {
            state.postValue(AuthorizationState.Content(data))
        }
    }

    private fun processError(message: String) {
        state.postValue(AuthorizationState.Error(message))
    }

    fun login(accessToken: String){
        viewModelScope.launch {
            authorizationInteractor
                .login(accessToken)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            processLogin(resource.data)
                        }
                        is Resource.Error -> {
                            processLoginError(resource.message.toString())
                        }
                    }
                }
        }
    }
    private fun processLogin(data: Login?) {
        if (data == null) {
            loginState.postValue(LoginState.Error("Получены пустые данные"))
        } else {
            loginState.postValue(LoginState.Content(data))
        }
    }
    private fun processLoginError(message: String) {
        state.postValue(AuthorizationState.Error(message))
    }

}