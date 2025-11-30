package com.example.sgdh.ui.solicitudes.status

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sgdh.databinding.ActivityStatusUpdateBinding
import com.example.sgdh.util.Validators
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusUpdateBinding
    private val viewModel: StatusUpdateViewModel by viewModels()

    private var solicitudId: Int = -1
    private var currentStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        solicitudId = intent.getIntExtra("SOLICITUD_ID", -1)
        currentStatus = intent.getStringExtra("CURRENT_STATUS") ?: ""

        if (solicitudId == -1) {
            Toast.makeText(this, "Solicitud no válida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        setupObservers()
        setupListeners()
        setupUI()
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
            binding.btnApprove.isEnabled = !state.isLoading
            binding.btnReject.isEnabled = !state.isLoading

            if (state.isStatusUpdated) {
                Toast.makeText(this, "Estado actualizado exitosamente", Toast.LENGTH_LONG).show()
                setResult(RESULT_OK)
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
        binding.btnApprove.setOnClickListener {
            viewModel.updateSolicitudStatus(solicitudId, "aprobada")
        }

        binding.btnReject.setOnClickListener {
            val motivoRechazo = binding.etMotivoRechazo.text.toString()

            if (!Validators.isValidJustification(motivoRechazo)) {
                binding.etMotivoRechazo.error = "El motivo de rechazo debe tener al menos 10 caracteres"
                return@setOnClickListener
            }

            viewModel.updateSolicitudStatus(solicitudId, "rechazada", motivoRechazo)
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioApprove.id -> {
                    binding.layoutMotivoRechazo.visibility = android.view.View.GONE
                    binding.btnApprove.visibility = android.view.View.VISIBLE
                    binding.btnReject.visibility = android.view.View.GONE
                }
                binding.radioReject.id -> {
                    binding.layoutMotivoRechazo.visibility = android.view.View.VISIBLE
                    binding.btnApprove.visibility = android.view.View.GONE
                    binding.btnReject.visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    private fun setupUI() {
        binding.tvSolicitudId.text = "Solicitud #$solicitudId"
        binding.tvCurrentStatus.text = "Estado actual: $currentStatus"

        // Verificar permisos basado en el estado actual
        when (currentStatus) {
            "pendiente_jefe" -> {
                binding.radioApprove.text = "Aprobar como Jefe"
                binding.radioReject.text = "Rechazar como Jefe"
            }
            "pendiente_farmacia" -> {
                binding.radioApprove.text = "Aprobar en Farmacia"
                binding.radioReject.text = "Rechazar en Farmacia"
            }
            else -> {
                // Si ya está aprobada o rechazada, deshabilitar cambios
                binding.radioGroup.visibility = android.view.View.GONE
                binding.buttonsLayout.visibility = android.view.View.GONE
                binding.tvMessage.visibility = android.view.View.VISIBLE
                binding.tvMessage.text = "Esta solicitud ya ha sido procesada y no puede ser modificada."
            }
        }
    }
}