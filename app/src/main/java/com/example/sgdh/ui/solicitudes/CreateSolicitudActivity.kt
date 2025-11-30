package com.example.sgdh.ui.solicitudes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sgdh.databinding.ActivityCreateSolicitudBinding
import com.example.sgdh.util.Validators
import com.example.sgdh.ui.solicitudes.create.CreateSolicitudViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSolicitudActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateSolicitudBinding
    private val viewModel: CreateSolicitudViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSolicitudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            binding.progressBar.visibility = if (state.isLoading) android.view.View.VISIBLE else android.view.View.GONE
            binding.btnCreate.isEnabled = !state.isLoading

            if (state.isSolicitudCreated) {
                Toast.makeText(this, "Solicitud creada exitosamente", Toast.LENGTH_LONG).show()
                finish()
            }

            state.error?.let { error ->
                binding.tvError.visibility = android.view.View.VISIBLE
                binding.tvError.text = error
            } ?: run {
                binding.tvError.visibility = android.view.View.GONE
            }
        }
    }

    private fun setupListeners() {
        binding.btnCreate.setOnClickListener {
            val justificacion = binding.etJustificacion.text.toString()

            if (!Validators.isValidJustification(justificacion)) {
                binding.etJustificacion.error = "La justificación debe tener al menos 10 caracteres"
                return@setOnClickListener
            }

            viewModel.onJustificacionChange(justificacion)
            viewModel.createSolicitud()
        }

        binding.etJustificacion.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val justificacion = binding.etJustificacion.text.toString()
                if (justificacion.isNotEmpty() && !Validators.isValidJustification(justificacion)) {
                    binding.etJustificacion.error = "La justificación debe tener al menos 10 caracteres"
                }
            }
        }
    }
}