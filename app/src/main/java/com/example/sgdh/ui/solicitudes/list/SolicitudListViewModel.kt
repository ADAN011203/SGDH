package com.example.sgdh.ui.solicitudes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SolicitudListState(
    val isLoading: Boolean = false,
    val solicitudes: List<Solicitud> = emptyList(),
    val error: String? = null
)

class SolicitudListViewModel : ViewModel() {

    private val repository = SolicitudRepository()

    private val _state = MutableStateFlow(SolicitudListState())
    val state: StateFlow<SolicitudListState> = _state.asStateFlow()

    fun getSolicitudes(estatus: String? = null) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            repository.getSolicitudes(estatus = estatus).fold(
                onSuccess = { solicitudes ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        solicitudes = solicitudes,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun refresh() {
        getSolicitudes()
    }
}