package com.example.sgdh.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.sgdh.data.models.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    private val gson = Gson()

    /**
     * Guarda el token de autenticación
     */
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    /**
     * Obtiene el token guardado
     */
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    /**
     * Limpia el token
     */
    fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    /**
     * Guarda los datos del usuario
     */
    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString(KEY_USER, userJson).apply()
    }

    /**
     * Obtiene los datos del usuario guardado
     */
    fun getUser(): User? {
        val userJson = prefs.getString(KEY_USER, null) ?: return null
        return try {
            gson.fromJson(userJson, User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Limpia los datos del usuario
     */
    fun clearUser() {
        prefs.edit().remove(KEY_USER).apply()
    }

    /**
     * Limpia toda la información de autenticación
     */
    fun clearAll() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "sgdh_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER = "user_data"
    }
}