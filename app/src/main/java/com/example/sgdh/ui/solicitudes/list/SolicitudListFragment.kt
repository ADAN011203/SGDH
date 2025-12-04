package com.example.sgdh.ui.solicitudes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sgdh.databinding.FragmentSolicitudListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SolicitudListFragment : Fragment() {

    private var _binding: FragmentSolicitudListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SolicitudListViewModel by viewModels()
    private lateinit var adapter: SolicitudAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolicitudListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
        observeUiState()

        // Botón para crear nueva solicitud
        binding.fabNewSolicitud.setOnClickListener {
            // Navegar a CreateSolicitudFragment
            // navController.navigate(R.id.action_list_to_create)
        }
    }

    private fun setupRecyclerView() {
        adapter = SolicitudAdapter { solicitud ->
            // Navegar al detalle
            // val action = SolicitudListFragmentDirections
            //     .actionListToDetail(solicitud.id)
            // navController.navigate(action)
        }

        binding.rvSolicitudes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SolicitudListFragment.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadSolicitudes()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // Actualizar UI según el estado
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.swipeRefresh.isRefreshing = state.isLoading  // ✅ CORRECCIÓN AQUÍ

                // Mostrar lista o mensaje vacío
                if (state.solicitudes.isEmpty() && !state.isLoading) {
                    binding.rvSolicitudes.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.rvSolicitudes.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                    adapter.submitList(state.solicitudes)
                }

                // Mostrar error si existe
                state.error?.let { error ->
                    Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
                        .setAction("Reintentar") {
                            viewModel.loadSolicitudes()
                        }
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}