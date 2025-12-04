package com.example.sgdh.ui.solicitudes.detail

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

data class SolicitudDetailUiState(
    val isLoading: Boolean = false,
    val solicitud: Solicitud? = null,
    val error: String? = null
)

@HiltViewModel
class SolicitudDetailViewModel @Inject constructor(
    private val solicitudRepository: SolicitudRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SolicitudDetailUiState())
    val uiState: StateFlow<SolicitudDetailUiState> = _uiState.asStateFlow()

    fun loadSolicitudDetail(solicitudId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val result = solicitudRepository.getSolicitudById(solicitudId)

                result.fold(
                    onSuccess = { solicitud ->
                        _uiState.value = SolicitudDetailUiState(
                            isLoading = false,
                            solicitud = solicitud,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = SolicitudDetailUiState(
                            isLoading = false,
                            solicitud = null,
                            error = exception.message ?: "Error al cargar el detalle"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = SolicitudDetailUiState(
                    isLoading = false,
                    solicitud = null,
                    error = e.message ?: "Error de conexión"
                )
            }
        }
    }
}