package com.example.tracker.registration.domain.interactor

import com.example.tracker.registration.data.RegistrationInteractor
import com.example.tracker.registration.domain.model.Registration
import com.example.tracker.registration.domain.repository.RegistrationRepository
import com.example.tracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationInteractorImpl(private val repository: RegistrationRepository) : RegistrationInteractor {
    override suspend fun registration(email: String, password: String): Flow<Resource<Registration>> = flow {
        repository.registration(email, password).collect { result ->
            emit(result)
        }
    }
}