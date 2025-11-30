package com.example.sgdh.ui.solicitudes.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.sgdh.ui.common.components.CustomTextField
import com.example.sgdh.ui.common.components.ErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSolicitudScreen(
    onSolicitudCreated: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: CreateSolicitudViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // CAMBIAR

) {
    val state by viewModel.state.collectAsState()  // <-- CAMBIO AQUÍ

    LaunchedEffect(key1 = state.isSolicitudCreated) {
        if (state.isSolicitudCreated) {
            onSolicitudCreated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.nueva_solicitud)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                value = state.justificacion,
                onValueChange = { viewModel.onJustificacionChange(it) },
                label = stringResource(id = R.string.justificacion),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.createSolicitud() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(id = R.string.crear_solicitud))
                }
            }

            state.error?.let { error ->
                ErrorMessage(
                    message = error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}