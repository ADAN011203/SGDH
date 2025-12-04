package com.example.sgdh.ui.solicitudes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sgdh.R
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.data.models.SolicitudStatus
import com.example.sgdh.databinding.FragmentSolicitudDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SolicitudDetailFragment : Fragment() {

    private var _binding: FragmentSolicitudDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SolicitudDetailViewModel by viewModels()
    private val solicitudId: Int by lazy {
        arguments?.getInt("solicitudId") ?: 0
    }

    private lateinit var productosAdapter: ProductosDetalleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolicitudDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeUiState()

        // Cargar detalles
        viewModel.loadSolicitudDetail(solicitudId)
    }

    private fun setupRecyclerView() {
        productosAdapter = ProductosDetalleAdapter()
        binding.recyclerViewProductos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productosAdapter
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // Mostrar/ocultar loading
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                // Mostrar error
                state.error?.let { error ->
                    Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.reintentar)) {
                            viewModel.loadSolicitudDetail(solicitudId)
                        }
                        .show()
                }

                // Mostrar solicitud
                state.solicitud?.let { solicitud ->
                    displaySolicitud(solicitud)
                }
            }
        }
    }

    private fun displaySolicitud(solicitud: Solicitud) {
        binding.apply {
            // Mostrar card
            cardInfo.visibility = View.VISIBLE

            // ID
            binding.textViewSolicitante.text = "Solicitud #${solicitud.id}"

            // Estado
            val status = SolicitudStatus.fromValue(solicitud.estatus)
            chipEstatus.text = status.displayName

            val bgColor: Int
            val textColor: Int


                binding.chipEstatus.text = when (solicitud.estatus) {
                    "pending_jefe" -> "Pendiente Jefe"
                    "pending_farmacia" -> "Pendiente Farmacia"
                    "approved" -> "Aprobada"
                    "rejected" -> "Rechazada"
                    "delivered" -> "Entregada"
                    else -> solicitud.estatus
                }

/*


            chipEstatus.setChipBackgroundColorResource(bgColor)
            chipEstatus.setTextColor(ContextCompat.getColor(requireContext(), textColor))
*/

            // Fecha

            binding.labelFecha.text = solicitud.createdAt
/*
            // Área
            textViewArea.text = solicitud.area?.nombre ?: "N/A"

            // Solicitante
            textViewSolicitante.text = solicitud.usuarioSolicitante?.name ?: "N/A"*/

            // Justificación
            textViewJustificacion.text = solicitud.justificacion

            // Productos
            if (!solicitud.detalles.isNullOrEmpty()) {
                textViewProductosTitle.visibility = View.VISIBLE
                recyclerViewProductos.visibility = View.VISIBLE
                textViewEmpty.visibility = View.GONE
                productosAdapter.submitList(solicitud.detalles)
            } else {
                textViewProductosTitle.visibility = View.VISIBLE
                recyclerViewProductos.visibility = View.GONE
                textViewEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}