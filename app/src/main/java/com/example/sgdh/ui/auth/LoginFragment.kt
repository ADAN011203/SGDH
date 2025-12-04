package com.example.sgdh.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sgdh.R
import com.example.sgdh.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeLoginState()
    }

    private fun setupListeners() {
        // Limpiar error cuando el usuario escribe
        binding.editTextEmail.addTextChangedListener {
            viewModel.clearError()
            binding.cardError.visibility = View.GONE
            binding.textInputLayoutEmail.error = null
        }

        binding.editTextPassword.addTextChangedListener {
            viewModel.clearError()
            binding.cardError.visibility = View.GONE
            binding.textInputLayoutPassword.error = null
        }

        // Botón de login
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        // Botón de Google Sign In
        binding.buttonGoogleSignIn.setOnClickListener {
            // TODO: Implementar Google Sign In
            Snackbar.make(
                binding.root,
                "Google Sign In no implementado aún",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                // Mostrar/ocultar loading
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.buttonLogin.isEnabled = !state.isLoading
                binding.buttonLogin.text = if (state.isLoading) "" else "Iniciar Sesión"
                binding.editTextEmail.isEnabled = !state.isLoading
                binding.editTextPassword.isEnabled = !state.isLoading
                binding.buttonGoogleSignIn.isEnabled = !state.isLoading

                // Mostrar error si existe
                if (state.error != null) {
                    binding.textViewError.text = state.error
                    binding.cardError.visibility = View.VISIBLE
                } else {
                    binding.cardError.visibility = View.GONE
                }

                // Navegar si el login fue exitoso
                //if (state.isSuccess) {
                  //  findNavController().navigate(
                    //    R.id.action_login_to_solicitudes
                    //)
                //}
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        when {
            email.isBlank() -> {
                binding.textInputLayoutEmail.error = getString(R.string.email_required)
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.textInputLayoutEmail.error = getString(R.string.email_invalid)
                return false
            }
            password.isBlank() -> {
                binding.textInputLayoutPassword.error = getString(R.string.password_required)
                return false
            }
            password.length < 6 -> {
                binding.textInputLayoutPassword.error = getString(R.string.password_min_length)
                return false
            }
            else -> {
                binding.textInputLayoutEmail.error = null
                binding.textInputLayoutPassword.error = null
                return true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}