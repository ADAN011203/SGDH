package com.example.sgdh.ui.solicitudes.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sgdh.R
import com.example.sgdh.ui.common.components.EmptyState
import com.example.sgdh.ui.common.components.ErrorMessage
import com.example.sgdh.ui.solicitudes.list.components.SolicitudCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudListScreen(
    onSolicitudClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SolicitudListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()  // <-- CAMBIO AQUÍ

    LaunchedEffect(key1 = Unit) {
        viewModel.getSolicitudes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.solicitudes)) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    ErrorMessage(
                        message = state.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                state.solicitudes.isEmpty() -> {
                    EmptyState(
                        message = "No hay solicitudes",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // CORRECCIÓN DE LAS LÍNEAS 77-78
                        items(state.solicitudes) { solicitud ->
                            SolicitudCard(  // <-- Cambio de SolicitudItem a SolicitudCard
                                solicitud = solicitud,
                                onClick = { onSolicitudClick(solicitud.id) }  // <-- CAMBIO AQUÍ
                            )
                        }
                    }
                }
            }
        }
    }
}