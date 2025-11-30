package com.example.sgdh.ui.solicitudes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SolicitudDetailState(
    val isLoading: Boolean = false,
    val solicitud: Solicitud? = null,
    val error: String? = null
)

class SolicitudDetailViewModel : ViewModel() {

    private val repository = SolicitudRepository()

    private val _state = MutableStateFlow(SolicitudDetailState())
    val state: StateFlow<SolicitudDetailState> = _state.asStateFlow()

    fun getSolicitud(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            repository.getSolicitud(id).fold(
                onSuccess = { solicitud ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        solicitud = solicitud,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error al cargar solicitud"
                    )
                }
            )
        }
    }

    fun updateStatus(estatus: String, motivoRechazo: String? = null) {
        val solicitudId = _state.value.solicitud?.id ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            repository.updateStatus(solicitudId, estatus, motivoRechazo).fold(
                onSuccess = { solicitud ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        solicitud = solicitud,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error al actualizar estatus"
                    )
                }
            )
        }
    }
}