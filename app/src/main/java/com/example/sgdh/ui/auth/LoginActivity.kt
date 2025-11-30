package com.example.sgdh.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sgdh.databinding.ActivityLoginBinding
import com.example.sgdh.ui.main.MainActivity
import com.example.sgdh.util.Validators
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            state?.let {
                binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                binding.btnLogin.isEnabled = !it.isLoading

                if (it.isLoginSuccessful) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }

                it.error?.let { error ->
                    Toast.makeText(this@LoginActivity, error, Toast.LENGTH_LONG).show()
                    // Opcional: limpiar el error después de mostrarlo
                    viewModel.clearError()
                }
            }
        }
    }

    // Solución alternativa si la anterior no funciona

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (!Validators.isValidEmail(email)) {
                binding.etEmail.error = "Email inválido"
                return@setOnClickListener
            }

            if (!Validators.isValidPassword(password)) {
                binding.etPassword.error = "Contraseña debe tener al menos 6 caracteres"
                return@setOnClickListener
            }

            viewModel.onEmailChange(email)
            viewModel.onPasswordChange(password)
            viewModel.login()
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.etEmail.text.toString()
                if (email.isNotEmpty() && !Validators.isValidEmail(email)) {
                    binding.etEmail.error = "Email inválido"
                } else {
                    binding.etEmail.error = null
                }
            }
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.etPassword.text.toString()
                if (password.isNotEmpty() && !Validators.isValidPassword(password)) {
                    binding.etPassword.error = "Contraseña debe tener al menos 6 caracteres"
                } else {
                    binding.etPassword.error = null
                }
            }
        }
    }
}