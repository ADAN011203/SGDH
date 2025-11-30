package com.example.sgdh.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.sgdh.data.models.User
import com.google.gson.Gson

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "sgdh_prefs",
        Context.MODE_PRIVATE
    )

    private val gson = Gson()

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER = "user_data"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString(KEY_USER, userJson).apply()
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
    }

    fun getUser(): User? {
        val userJson = prefs.getString(KEY_USER, null) ?: return null
        return try {
            gson.fromJson(userJson, User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}