package com.example.sgdh.ui.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)

            val result = authRepository.login(email, password)

            result.onSuccess {
                _loginState.value = LoginState(isSuccess = true)
            }.onFailure { exception ->
                _loginState.value = LoginState(
                    error = exception.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    fun loginWithGoogle(googleToken: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)

            val result = authRepository.loginWithGoogle(googleToken)

            result.onSuccess {
                _loginState.value = LoginState(isSuccess = true)
            }.onFailure { exception ->
                _loginState.value = LoginState(
                    error = exception.message ?: "Error al iniciar sesión con Google"
                )
            }
        }
    }

    fun clearError() {
        _loginState.value = _loginState.value.copy(error = null)
    }
}