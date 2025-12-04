package com.example.sgdh.data.repository

import com.example.sgdh.data.models.AuthResponse
import com.example.sgdh.data.models.GoogleLoginRequest
import com.example.sgdh.data.models.LoginRequest
import com.example.sgdh.data.remote.ApiService
import com.example.sgdh.data.local.TokenManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    /**
     * Login con email y contraseña
     */
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                // Guardar el token
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveUser(authResponse.user)
                Result.success(authResponse)
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Credenciales incorrectas"
                    403 -> "Usuario no autorizado"
                    404 -> "Usuario no encontrado"
                    else -> "Error al iniciar sesión: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Login con Google
     */
    suspend fun loginWithGoogle(googleToken: String): Result<AuthResponse> {
        return try {
            val request = GoogleLoginRequest(googleToken)
            val response = apiService.loginWithGoogle(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                // Guardar el token
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveUser(authResponse.user)
                Result.success(authResponse)
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Token de Google inválido"
                    403 -> "Usuario no autorizado en el sistema"
                    404 -> "Usuario no registrado"
                    else -> "Error al iniciar sesión con Google: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Logout - revoca el token en el servidor
     */
    suspend fun logout(): Result<Unit> {
        return try {
            val response = apiService.logout()
            tokenManager.clearToken()
            tokenManager.clearUser()

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                // Aunque falle en servidor, limpiamos local
                Result.success(Unit)
            }
        } catch (e: Exception) {
            // Aunque falle, limpiamos local
            Result.success(Unit)
        }
    }

    /**
     * Verifica si hay un usuario logueado
     */
    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    /**
     * Obtiene el token actual
     */
    fun getToken(): String? {
        return tokenManager.getToken()
    }
}