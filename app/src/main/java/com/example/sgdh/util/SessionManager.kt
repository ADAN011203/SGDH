package com.example.sgdh.util

import android.content.Context
import android.content.SharedPreferences
import com.example.sgdh.data.models.User

object SessionManager {

    private const val PREF_NAME = "SGDH_Session"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_ROL = "user_rol"
    private const val KEY_LAST_SYNC = "last_sync"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserSession(context: Context, user: User) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME, user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.putString(KEY_USER_ROL, user.rol)
        editor.putLong(KEY_LAST_SYNC, System.currentTimeMillis())
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUser(context: Context): User? {
        val prefs = getPreferences(context)
        return if (prefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            User(
                id = prefs.getInt(KEY_USER_ID, 0),
                name = prefs.getString(KEY_USER_NAME, "") ?: "",
                email = prefs.getString(KEY_USER_EMAIL, "") ?: "",
                rol = prefs.getString(KEY_USER_ROL, "") ?: "",
                area_id = null
            )
        } else {
            null
        }
    }

    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

    fun getLastSyncTime(context: Context): Long {
        return getPreferences(context).getLong(KEY_LAST_SYNC, 0)
    }

    fun updateLastSyncTime(context: Context) {
        val editor = getPreferences(context).edit()
        editor.putLong(KEY_LAST_SYNC, System.currentTimeMillis())
        editor.apply()
    }
}