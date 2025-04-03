package com.example.tracker.authorization.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.AuthorizationFragmentBinding
import com.example.tracker.util.AuthorizationState
import com.example.tracker.util.LoginState
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {
    private var _binding: AuthorizationFragmentBinding? = null
    val binding: AuthorizationFragmentBinding
        get() = _binding!!
    private val viewModel by viewModel<AuthorizationViewModel>()
    private var emal = ""
    private var pass = ""
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupObservers()

        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_recoveryFragment)
        }
        binding.registration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        binding.btApply.setOnClickListener {
            viewModel.loadData(emal, pass)
            viewModel.login(token)
        }
    }

    private fun setupTextWatcher() {
        val emailErrorMessage = "Пожалуйста, введите корректный Email"
        val passwordLengthErrorMessage = "Пароль должен содержать не менее 7 символов"

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emal = s.toString()
                if (emal.isBlank()) {
                    showError(binding.errorEmail, emailErrorMessage)
                } else {
                    hideError(binding.errorEmail)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pass = s.toString()
                if (pass.length < 7) {
                    showError(binding.errorPass, passwordLengthErrorMessage)
                } else {
                    hideError(binding.errorPass)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showError(view: TextView, message: String) {
        view.isVisible = true
        view.text = message
    }

    private fun hideError(view: TextView) {
        view.isVisible = false
    }

    private fun checkingInput() {
        binding.btApply.isEnabled =
            emal.isNotBlank() && pass.length >= 7
    }

    private fun setupObservers() {
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthorizationState.Empty -> {
                    Log.d("authorization", "Пустой список данных")
                }

                is AuthorizationState.Error -> {
                    Log.e("authorization", "Ошибка: ${state.message}")
                }

                is AuthorizationState.Content -> {
                    state.data?.let {
                        Log.e("authorization", "успех: ${it}")
                        token = state.data.accessToken
                    }
                }
            }
        }
        viewModel.getLoginState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Empty -> {
                    Log.d("login", "Пустой список данных")
                }

                is LoginState.Error -> {
                    Log.e("login", "Ошибка: ${state.message}")
                }

                is LoginState.Content -> {
                    state.data?.let {
                        Log.e("login", "успех: ${it}")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}