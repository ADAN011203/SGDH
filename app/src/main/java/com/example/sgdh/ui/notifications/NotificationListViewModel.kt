package com.example.sgdh.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.notifications.Notification
import com.example.sgdh.data.repository.NotificacionRepository
import com.example.sgdh.domain.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val notificationRepository: NotificacionRepository
) : ViewModel() {

    private val _state = MutableLiveData(NotificationListState())
    val state: LiveData<NotificationListState> = _state

    private val _markAsReadResult = MutableLiveData<MarkAsReadResult?>()
    val markAsReadResult: LiveData<MarkAsReadResult?> = _markAsReadResult

    init {
        getNotifications()
    }

    fun getNotifications(unread: Boolean? = null, limit: Int? = null) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = notificationRepository.getNotifications(
                limit = limit ?: 25,
                unread = unread
            )

            when (result) {
                is Result.Companion -> {
                    val notifications = result.getOrNull()?.data ?: emptyList()
                    val unreadCount = notifications.count { it.readAt == null }
                    _state.value = _state.value.copy(
                        notifications = notifications,
                        unreadCount = unreadCount,
                        isLoading = false
                    )
                }
                is Result.Companion -> {
                    _state.value = _state.value.copy(
                        error = result.exceptionOrNull()?.message ?: "Error desconocido",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            val result = notificationRepository.markAsRead(notificationId)
            _markAsReadResult.value = MarkAsReadResult(
                notificationId = notificationId,
                isSuccess = result is Resource.Success<*>
            )
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            val currentNotifications = _state.value?.notifications ?: emptyList()
            currentNotifications.forEach { notification ->
                if (notification.read_at == null) {
                    notificationRepository.markAsRead(notification.id)
                }
            }
            getNotifications() // Refresh the list
        }
    }
}

// Data classes FUERA de la clase ViewModel
data class NotificationListState(
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MarkAsReadResult(
    val notificationId: String,
    val isSuccess: Boolean
)