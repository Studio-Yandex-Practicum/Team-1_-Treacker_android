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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tracker.databinding.RegistrationFragmentBinding

import com.example.tracker.util.RegistrationState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private var _binding: RegistrationFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegistrationViewModel>()
    private var emal = ""
    private var passFirst = ""
    private var passSecond = ""

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
        val passwordLengthErrorMessage = "Пароль должен содержать не менее 7 символов"
        val passwordMismatchErrorMessage = "Пароли не совпадают"

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
                passFirst = s.toString()
                if (passFirst.length < 7) {
                    showError(binding.errorPass, passwordLengthErrorMessage)
                } else {
                    hideError(binding.errorPass)
                }
                checkingInput()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPass2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passSecond = s.toString()
                if (passSecond.length < 7) {
                    showError(binding.errorPass2, passwordLengthErrorMessage)
                } else if (passSecond != passFirst) {
                    showError(binding.errorPass2, passwordMismatchErrorMessage)
                } else {
                    hideError(binding.errorPass2)
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
            emal.isNotBlank() && passFirst.length >= 7 && passSecond.length >= 7 && passFirst == passSecond
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
                        lifecycleScope.launch {
                            Log.e("login", "успех: ${it}")
                            viewModel.setAccessToken(it.accessToken)
                            viewModel.setRefreshToken(it.refreshToken)
                            viewModel.setIdToken(it.userId)
                        }
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