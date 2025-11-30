package com.example.sgdh.data.repository

import android.util.Log
import com.example.sgdh.data.api.PharmacyApiService
import com.example.sgdh.data.api.RetrofitClient
import com.example.sgdh.data.local.PreferencesManager
import com.example.sgdh.data.models.auth.LoginRequest
import com.example.sgdh.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: PharmacyApiService,
    private val sharedPrefManager: PreferencesManager
) {

    private companion object {
        const val TAG = "AuthRepository"
    }

    suspend fun login(email: String, password: String, deviceName: String? = null): Resource<Unit> {
        return try {
            Log.d(TAG, "Intentando login con email: $email")

            val request = LoginRequest(email, password, deviceName)
            Log.d(TAG, "Request: ${request.email}, device: ${request.device_name}")

            val response = apiService.login(request)

            Log.d(TAG, "Código de respuesta: ${response.code()}")
            Log.d(TAG, "¿Es exitosa?: ${response.isSuccessful}")
            Log.d(TAG, "Mensaje: ${response.message()}")

            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    Log.d(TAG, "Login exitoso - User: ${loginResponse.user.name}")
                    Log.d(TAG, "Token recibido: ${loginResponse.token.take(10)}...")
                    Log.d(TAG, "Abilities: ${loginResponse.abilities}")

                    RetrofitClient.setAuthToken(loginResponse.token)
                    sharedPrefManager.saveToken(loginResponse.token)
                    sharedPrefManager.saveUser(loginResponse.user)
                    //sharedPrefManager.saveAbilities(loginResponse.abilities)

                    Resource.Success(Unit)
                } ?: run {
                    Log.e(TAG, "Respuesta vacía del servidor")
                    Resource.Error("Respuesta vacía del servidor")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Error del servidor - Código: ${response.code()}")
                Log.e(TAG, "Error body: $errorBody")
                Resource.Error("Credenciales incorrectas - Código: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de conexión: ${e.message}", e)
            Resource.Error("Error de conexión: ${e.message}")
        }
    }

    suspend fun logout(): Resource<Unit> {
        return try {
            val response = apiService.logout()
            if (response.isSuccessful) {
                RetrofitClient.clearAuthToken()
                sharedPrefManager.clearSession()
                Resource.Success(Unit)
            } else {
                Resource.Error("Error al cerrar sesión")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error de conexión")
        }
    }

    fun getCurrentUser() = sharedPrefManager.getUser()
    fun isLoggedIn() = sharedPrefManager.getToken() != null
    //fun getUserAbilities() = sharedPrefManager.getAbilities()
}