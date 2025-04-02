package com.example.tracker.registration.ui

import android.content.Context
import android.content.SharedPreferences
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
import com.example.tracker.databinding.RegistrationFragmentBinding

import com.example.tracker.util.RegistrationState
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private var _binding: RegistrationFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegistrationViewModel>()
    private var emal = ""
    private var passFirst = ""
    private var passSecond = ""
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        setupTextWatcher()
        setupObservers()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btApply.setOnClickListener {
            viewModel.loadData(emal, passFirst)
        }
    }

    private fun setupTextWatcher() {
        val emailErrorMessage = "Пожалуйста, введите корректный Email"
        val passwordLengthErrorMessage = "Пароль должен содержать не менее 6 символов"
        val passwordMismatchErrorMessage = "Пароли не совпадают"

        val emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (email.isBlank()) {
                    showError(binding.errorEmail, emailErrorMessage)
                } else {
                    hideError(binding.errorEmail)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etEmail.addTextChangedListener(emailTextWatcher)

        val passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.etPass.text.toString()
                if (password.length < 6) {
                    showError(binding.errorPass, passwordLengthErrorMessage)
                } else {
                    hideError(binding.errorPass)
                }
                if (binding.etEmail.length() < 1) {
                    showError(binding.errorEmail, emailErrorMessage)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etPass.addTextChangedListener(passwordTextWatcher)

        val ConfirmTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.etPass.text.toString()
                val confirmPassword = s.toString()
                if (confirmPassword.length < 6) {
                    showError(binding.errorPass2, passwordLengthErrorMessage)
                } else if (password != confirmPassword) {
                    showError(binding.errorPass2, passwordMismatchErrorMessage)
                } else {
                    hideError(binding.errorPass2)
                }
                if (binding.etEmail.length() < 1) {
                    showError(binding.errorEmail, emailErrorMessage)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etPass2.addTextChangedListener(ConfirmTextWatcher)
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
            emal.isNotBlank() && passFirst.length > 6 && passSecond.length > 6 && passFirst == passSecond
    }

    private fun setupObservers() {
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegistrationState.Empty -> {
                    Log.d("login", "Пустой список данных")
                }

                is RegistrationState.Error -> {
                    Log.e("login", "Ошибка: ${state.message}")
                }

                is RegistrationState.Content -> {
                    state.data?.let {
                        Log.e("login", "успех: ${it}")
                        editor.putString("access_token", it.accessToken)
                        editor.putString("refresh_token", it.refreshToken)
                        editor.putString("user_id", it.userId.toString())
                        editor.apply()
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