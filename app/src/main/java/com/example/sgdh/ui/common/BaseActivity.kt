package com.example.sgdh.ui.common

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

abstract class BaseActivity : ComponentActivity() {
    // Puedes agregar funcionalidades comunes para todas las actividades aquí
}

@Composable
inline fun <reified VM : androidx.lifecycle.ViewModel> baseViewModel(): VM {
    return viewModel(modelClass = VM::class.java)
}