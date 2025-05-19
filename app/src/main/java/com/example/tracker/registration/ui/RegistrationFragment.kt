package com.example.tracker.registration.ui

import android.os.Bundle
import android.text.Editable
import android.text.InputType
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
import com.example.tracker.databinding.RegistrationFragmentBinding

import com.example.tracker.util.RegistrationState
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private var _binding: RegistrationFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegistrationViewModel>()
    private var email = ""
    private var passFirst = ""
    val validDomains = listOf("@mail.ru", "@gmail.com", "@yandex.ru")


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
            viewModel.loadData(email, passFirst)
        }
        binding.titlePass.setEndIconOnClickListener {
            val isPasswordVisible =
                binding.titlePass.editText?.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            if (isPasswordVisible) {
                binding.titlePass.editText?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.titlePass.setEndIconDrawable(R.drawable.icon_pass_close)
            } else {
                binding.titlePass.editText?.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.titlePass.setEndIconDrawable(R.drawable.icon_pass_open)
            }
            binding.titlePass.editText?.setSelection(binding.titlePass.editText?.text?.length ?: 0)
        }
    }

    private fun setupTextWatcher() {
        val emailErrorMessage = "Пожалуйста, введите корректный Email"
        val passwordLengthErrorMessage = "Пароль должен содержать не менее 7 символов"

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email = s.toString()
                if (email.isBlank() || validDomains.none { email.endsWith(it) }) {
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
            email.isNotBlank() && passFirst.length >= 7
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
                        viewModel.fetchSetToken(it.accessToken, it.refreshToken, it.userId)
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