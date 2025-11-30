package com.example.sgdh.ui.solicitudes.create.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun JustificationField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Justificación") },
        modifier = modifier,
        singleLine = false,
        maxLines = 4,
        isError = isError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        supportingText = {
            if (isError) {
                Text("La justificación debe tener al menos 10 caracteres")
            }
        }
    )
}