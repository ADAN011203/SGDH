package com.example.sgdh.ui.solicitudes.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sgdh.data.repository.SolicitudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusUpdateViewModel @Inject constructor(
    private val solicitudRepository: SolicitudRepository
) : ViewModel() {

    private val _state = MutableLiveData(StatusUpdateState())
    val state: LiveData<StatusUpdateState> = _state

    fun updateSolicitudStatus(id: Int, estatus: String, motivoRechazo: String? = null) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true, error = null)
            val result = solicitudRepository.updateSolicitudStatus(id, estatus, motivoRechazo)
            _state.value = _state.value?.copy(isLoading = false)

            when (result) {
                is com.example.sgdh.domain.models.Resource.Success -> {
                    _state.value = _state.value?.copy(isStatusUpdated = true)
                }
                is com.example.sgdh.domain.models.Resource.Error -> {
                    _state.value = _state.value?.copy(error = result.message)
                }
                else -> {}
            }
        }
    }
}

data class StatusUpdateState(
    val isLoading: Boolean = false,
    val isStatusUpdated: Boolean = false,
    val error: String? = null
)