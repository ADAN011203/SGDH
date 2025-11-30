package com.example.sgdh.domain.usecases

import com.example.sgdh.data.models.solicitudes.Solicitud
import com.example.sgdh.data.repository.SolicitudRepository
import com.example.sgdh.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSolicitudesUseCase @Inject constructor(
    private val solicitudRepository: SolicitudRepository
) {
    operator fun invoke(estatus: String? = null, perPage: Int? = null): Flow<Resource<List<Solicitud>>> = flow {
        emit(Resource.Loading())
        try {
            val result = solicitudRepository.getSolicitudes(estatus, perPage)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}