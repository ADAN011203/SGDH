package com.example.sgdh.ui.solicitudes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sgdh.R
import com.example.sgdh.databinding.FragmentCreateSolicitudBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSolicitudFragment : Fragment() {

    private var _binding: FragmentCreateSolicitudBinding? = null
    private val binding get() = _binding!!

    // TODO: Descomentar cuando implementes el ViewModel
    // private val viewModel: CreateSolicitudViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSolicitudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        // Botón para crear la solicitud
        binding.buttonCreateSolicitud.setOnClickListener {
            val justificacion = binding.editTextJustificacion.text.toString()

            if (validateForm(justificacion)) {
                // TODO: Llamar al ViewModel para crear la solicitud
                // viewModel.createSolicitud(justificacion)

                Snackbar.make(
                    binding.root,
                    "Funcionalidad de creación pendiente de implementar",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        // Botón para agregar productos
        binding.buttonAddProduct.setOnClickListener {
            // TODO: Mostrar diálogo o navegar a pantalla de selección de productos
            Snackbar.make(
                binding.root,
                "Selección de productos pendiente de implementar",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateForm(justificacion: String): Boolean {
        return when {
            justificacion.isBlank() -> {
                binding.textInputLayoutJustificacion.error = "La justificación es requerida"
                false
            }
            justificacion.length < 10 -> {
                binding.textInputLayoutJustificacion.error = "Mínimo 10 caracteres"
                false
            }
            else -> {
                binding.textInputLayoutJustificacion.error = null
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}