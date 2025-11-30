package com.example.sgdh.ui.solicitudes.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sgdh.ui.common.components.ErrorMessage
import com.example.sgdh.ui.solicitudes.create.CreateSolicitudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudDetailScreen(
    solicitudId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: CreateSolicitudViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // CAMBIAR

) {
    val state by viewModel.state.collectAsState()  // <-- CAMBIO AQUÍ

    LaunchedEffect(key1 = solicitudId) {
        if (solicitudId != null) {
            viewModel.getSolicitud(solicitudId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Solicitud") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                state.error != null -> {
                    ErrorMessage(
                        message = state.error,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                }
                state.solicitud != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Solicitud #${state.solicitud.id}")
                        Text(text = "Estado: ${state.solicitud.estatus}")
                        Text(text = "Fecha: ${state.solicitud.fecha_solicitud}")
                        Text(text = "Justificación: ${state.solicitud.justificacion}")
                    }
                }
            }
        }
    }
}