package com.example.sgdh.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.repository.AuthRepository
import com.example.sgdh.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    fun onEmailChange(email: String) {
        _state.value = _state.value?.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value?.copy(password = password)
    }

    fun login(deviceName: String? = null) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true, error = null)
            val result = authRepository.login(
                _state.value?.email ?: "",
                _state.value?.password ?: "",
                deviceName
            )
            _state.value = _state.value?.copy(isLoading = false)
            when (result) {
                is com.example.sgdh.domain.models.Resource.Success -> {
                    _state.value = _state.value?.copy(isLoginSuccessful = true)
                }
                is com.example.sgdh.domain.models.Resource.Error -> {
                    _state.value = _state.value?.copy(error = result.message)
                }
                else -> {}
            }
        }
    }

    fun clearError() {
        _state.value = _state.value?.copy(error = null)
    }
}