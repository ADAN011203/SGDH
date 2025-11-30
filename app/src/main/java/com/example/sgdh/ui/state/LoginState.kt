package com.example.sgdh.ui.state

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val error: String? = null
)