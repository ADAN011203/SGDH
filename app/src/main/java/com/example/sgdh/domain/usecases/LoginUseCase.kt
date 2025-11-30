package com.example.sgdh.domain.usecases

import com.example.sgdh.data.repository.AuthRepository
import com.example.sgdh.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String, deviceName: String? = null): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val result = authRepository.login(email, password, deviceName)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}