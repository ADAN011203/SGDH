package com.example.sgdh.ui.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.Notificacion
import com.example.sgdh.data.repository.NotificacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<Notificacion> = emptyList(),
    val unreadCount: Int = 0,
    val error: String? = null
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificacionRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            repository.getNotificaciones().fold(
                onSuccess = { response ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        notifications = response.data,
                        unreadCount = response.meta?.unreadCount ?: 0,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            repository.markAsRead(notificationId).fold(
                onSuccess = {
                    loadNotifications()
                },
                onFailure = { /* Handle error */ }
            )
        }
    }
}