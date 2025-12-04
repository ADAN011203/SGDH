package com.example.sgdh.ui.solicitudes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.data.repository.SolicitudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SolicitudListUiState(
    val isLoading: Boolean = false,
    val solicitudes: List<Solicitud> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class SolicitudListViewModel @Inject constructor(
    private val solicitudRepository: SolicitudRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SolicitudListUiState())
    val uiState: StateFlow<SolicitudListUiState> = _uiState.asStateFlow()

    init {
        loadSolicitudes()
    }

    fun loadSolicitudes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val result = solicitudRepository.getSolicitudes()

                result.fold(
                    onSuccess = { solicitudes ->
                        _uiState.value = SolicitudListUiState(
                            isLoading = false,
                            solicitudes = solicitudes,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = SolicitudListUiState(
                            isLoading = false,
                            solicitudes = emptyList(),
                            error = exception.message ?: "Error al cargar solicitudes"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = SolicitudListUiState(
                    isLoading = false,
                    solicitudes = emptyList(),
                    error = e.message ?: "Error de conexión"
                )
            }
        }
    }

    fun refreshSolicitudes() {
        loadSolicitudes()
    }
}