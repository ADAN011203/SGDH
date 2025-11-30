package com.example.sgdh.ui.solicitudes.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.CreateSolicitudRequest
import com.example.sgdh.data.models.DetalleRequest
import com.example.sgdh.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateSolicitudState(
    val isLoading: Boolean = false,
    val justificacion: String = "",
    val detalles: List<DetalleItem> = emptyList(),
    val error: String? = null,
    val isSolicitudCreated: Boolean = false
)

data class DetalleItem(
    val productoId: Int,
    val productoNombre: String,
    val cantidad: Int
)

class CreateSolicitudViewModel : ViewModel() {

    private val repository = SolicitudRepository()

    private val _state = MutableStateFlow(CreateSolicitudState())
    val state: StateFlow<CreateSolicitudState> = _state.asStateFlow()

    fun onJustificacionChange(justificacion: String) {
        _state.value = _state.value.copy(justificacion = justificacion)
    }

    fun addDetalle(productoId: Int, productoNombre: String, cantidad: Int) {
        val currentDetalles = _state.value.detalles.toMutableList()
        currentDetalles.add(DetalleItem(productoId, productoNombre, cantidad))
        _state.value = _state.value.copy(detalles = currentDetalles)
    }

    fun removeDetalle(index: Int) {
        val currentDetalles = _state.value.detalles.toMutableList()
        if (index in currentDetalles.indices) {
            currentDetalles.removeAt(index)
            _state.value = _state.value.copy(detalles = currentDetalles)
        }
    }

    fun createSolicitud() {
        val currentState = _state.value

        if (currentState.justificacion.isBlank()) {
            _state.value = currentState.copy(error = "La justificación es requerida")
            return
        }

        if (currentState.detalles.isEmpty()) {
            _state.value = currentState.copy(error = "Debe agregar al menos un producto")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            val request = CreateSolicitudRequest(
                justificacion = currentState.justificacion,
                detalles = currentState.detalles.map {
                    DetalleRequest(it.productoId, it.cantidad)
                }
            )

            repository.createSolicitud(request).fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSolicitudCreated = true,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error al crear solicitud"
                    )
                }
            )
        }
    }
}