package com.example.sgdh.ui.solicitudes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sgdh.R
import com.example.sgdh.databinding.ActivitySolicitudDetailBinding
import com.example.sgdh.ui.solicitudes.detail.SolicitudDetailViewModel
import com.example.sgdh.ui.solicitudes.status.StatusUpdateActivity
import com.example.sgdh.util.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolicitudDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySolicitudDetailBinding
    private val viewModel: SolicitudDetailViewModel by viewModels()

    private var solicitudId: Int = -1
    private var currentSolicitud: com.example.sgdh.data.models.solicitudes.Solicitud? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        solicitudId = intent.getIntExtra("SOLICITUD_ID", -1)
        if (solicitudId == -1) {
            finish()
            return
        }

        setupToolbar()
        setupObservers()
        viewModel.getSolicitud(solicitudId)
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

            if (state.error != null) {
                binding.tvError.visibility = android.view.View.VISIBLE
                binding.tvError.text = state.error
                binding.scrollView.visibility = android.view.View.GONE
            } else if (state.solicitud != null) {
                currentSolicitud = state.solicitud
                binding.scrollView.visibility = android.view.View.VISIBLE
                binding.tvError.visibility = android.view.View.GONE
                displaySolicitud(state.solicitud)
                invalidateOptionsMenu() // Actualizar menú según el estado
            }
        }
    }

    private fun displaySolicitud(solicitud: com.example.sgdh.data.models.solicitudes.Solicitud) {
        binding.tvSolicitudId.text = "Solicitud #${solicitud.id}"
        binding.tvEstado.text = "Estado: ${solicitud.estatus.label}"
        binding.tvFecha.text = "Fecha: ${DateUtils.formatDate(solicitud.fecha_solicitud)}"
        binding.tvJustificacion.text = solicitud.justificacion
        binding.tvArea.text = "Área: ${solicitud.area.nombre}"
        binding.tvSolicitante.text = "Solicitante: ${solicitud.usuario_solicitante.nombre}"
        binding.tvActualizada.text = "Actualizada: ${DateUtils.formatDate(solicitud.actualizada_en)}"

        // Mostrar detalles de productos
        val detallesText = StringBuilder()
        solicitud.detalles.forEachIndexed { index, detalle ->
            detallesText.append("${index + 1}. ${detalle.producto.descripcion}\n")
            detallesText.append("   Clave: ${detalle.producto.clave}\n")
            detallesText.append("   Cantidad: ${detalle.cantidad_solicitada}\n\n")
        }
        binding.tvDetalles.text = detallesText.toString()

        // Configurar color según estado
        when (solicitud.estatus.value) {
            "aprobada" -> binding.tvEstado.setTextColor(getColor(R.color.success))
            "rechazada" -> binding.tvEstado.setTextColor(getColor(R.color.error))
            else -> binding.tvEstado.setTextColor(getColor(R.color.warning))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_solicitud_detail, menu)

        // Mostrar/ocultar opción de edición basado en el estado
        val canEdit = currentSolicitud?.estatus?.value in listOf("pendiente_jefe", "pendiente_farmacia")
        menu.findItem(R.id.menu_edit_status).isVisible = canEdit

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit_status -> {
                currentSolicitud?.let { solicitud ->
                    val intent = Intent(this, StatusUpdateActivity::class.java).apply {
                        putExtra("SOLICITUD_ID", solicitud.id)
                        putExtra("CURRENT_STATUS", solicitud.estatus.value)
                    }
                    startActivityForResult(intent, REQUEST_STATUS_UPDATE)
                }
                true
            }
            R.id.menu_refresh -> {
                viewModel.getSolicitud(solicitudId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_STATUS_UPDATE && resultCode == RESULT_OK) {
            // Recargar los datos si se actualizó el estado
            viewModel.getSolicitud(solicitudId)
        }
    }

    companion object {
        private const val REQUEST_STATUS_UPDATE = 1001
    }
}