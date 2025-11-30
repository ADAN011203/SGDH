package com.example.sgdh.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sgdh.databinding.ActivityProfileBinding
import com.example.sgdh.ui.auth.LoginActivity
import com.example.sgdh.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        displayUserInfo()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun displayUserInfo() {
        val user = viewModel.getCurrentUser()
        user?.let {
            binding.tvName.text = it.name
            binding.tvEmail.text = it.email
            binding.tvRol.text = it.rol
            binding.tvAreaId.text = it.area_id?.toString() ?: "No asignado"
        }

        val abilities = viewModel.getUserAbilities()
        val abilitiesText = abilities.joinToString("\n")
        binding.tvAbilities.text = abilitiesText
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.btnChangePassword.setOnClickListener {
            // TODO: Implementar cambio de contraseña
            android.widget.Toast.makeText(this, "Funcionalidad en desarrollo", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}