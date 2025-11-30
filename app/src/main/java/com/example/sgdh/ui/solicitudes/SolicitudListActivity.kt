package com.example.sgdh.ui.solicitudes

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sgdh.databinding.ActivitySolicitudListBinding
import com.example.sgdh.ui.solicitudes.list.SolicitudListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolicitudListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySolicitudListBinding
    private val viewModel: SolicitudListViewModel by viewModels()
    private lateinit var adapter: SolicitudAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        viewModel.getSolicitudes()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerView() {
        adapter = SolicitudAdapter { solicitud ->
            val intent = Intent(this, SolicitudDetailActivity::class.java).apply {
                putExtra("SOLICITUD_ID", solicitud.id)
            }
            startActivity(intent)
        }
        binding.rvSolicitudes.layoutManager = LinearLayoutManager(this)
        binding.rvSolicitudes.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            binding.progressBar.visibility = if (state.isLoading) android.view.View.VISIBLE else android.view.View.GONE

            if (state.error != null) {
                binding.tvError.visibility = android.view.View.VISIBLE
                binding.tvError.text = state.error
                binding.rvSolicitudes.visibility = android.view.View.GONE
                binding.tvEmpty.visibility = android.view.View.GONE
            } else if (state.solicitudes.isEmpty()) {
                binding.tvEmpty.visibility = android.view.View.VISIBLE
                binding.tvEmpty.text = "No hay solicitudes"
                binding.rvSolicitudes.visibility = android.view.View.GONE
                binding.tvError.visibility = android.view.View.GONE
            } else {
                binding.rvSolicitudes.visibility = android.view.View.VISIBLE
                adapter.submitList(state.solicitudes)
                binding.tvError.visibility = android.view.View.GONE
                binding.tvEmpty.visibility = android.view.View.GONE
            }
        }
    }
}