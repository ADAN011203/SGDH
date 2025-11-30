package com.example.sgdh.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun getCurrentUser() = authRepository.getCurrentUser()
    fun isLoggedIn() = authRepository.isLoggedIn()
    fun getUserAbilities() = authRepository.getUserAbilities()
}