package com.example.tracker.authorization.ui

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
import com.example.tracker.R
import com.example.tracker.databinding.AuthorizationFragmentBinding
import com.example.tracker.util.AuthorizationState
import com.example.tracker.util.LoginState
import com.example.tracker.util.RefreshState
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {
    private var _binding: AuthorizationFragmentBinding? = null
    val binding: AuthorizationFragmentBinding
        get() = _binding!!
    private val viewModel by viewModel<AuthorizationViewModel>()
    private var emal = ""
    private var pass = ""
    private var refreshToken = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

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
        sharedPreferences =
            requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setupObserveCheck()
        setupTextWatcher()
        setupObservers()
        setupObserveLogin()
        checkLogin()

        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_recoveryFragment)
        }
        binding.registration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        binding.btApply.setOnClickListener {
            viewModel.loadData(emal, pass)
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
                    Log.e("authorization", "${state.message}")
                    if (state.message.equals("Ошибка: 401 - ")) {
                        refreshToken = sharedPreferences.getString("refresh_token", null) ?: ""
                        viewModel.refresh(refreshToken)
                    }
                }

                is AuthorizationState.Content -> {
                    state.data?.let {
                        Log.e("authorization", "успех: ${it}")
                        viewModel.login(state.data.accessToken)
                        editor.putString("access_token", it.accessToken)
                        editor.putString("refresh_token", it.refreshToken)
                        editor.putString("user_id", it.userId.toString())
                        editor.apply()
                    }
                }
            }
        }
    }

    private fun setupObserveCheck() {
        viewModel.getRefreshState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RefreshState.Empty -> {
                    Log.d("refresh", "Пустой список данных")
                }

                is RefreshState.Error -> {
                    if (state.message.equals("Ошибка: 401 - ")) {
                        Log.d("refresh", "Авторизуйтесь")
                    }
                }

                is RefreshState.Content -> {
                    state.data?.let {
                        Log.e("refresh", "успех: ${it}")
                        viewModel.login(state.data.accessToken)
                    }
                }
            }
        }

    }

    private fun setupObserveLogin() {
        viewModel.getLoginState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Empty -> {
                    Log.d("login", "Пустой список данных")
                }

                is LoginState.Error -> {
                    Log.e("login", "${state.message}")
                    if (state.message.equals("Ошибка: 401 - ")) {
                        refreshToken = sharedPreferences.getString("refresh_token", null) ?: ""
                        viewModel.refresh(refreshToken)
                    } else if (state.message.equals("Сетевая ошибка: Failed to connect to /130.193.44.66:8080")){
                        Log.e("login", "вход без проверки")
                    }
                }

                is LoginState.Content -> {
                    state.data?.let {
                        Log.e("login", "успех: ${it}")
                    }
                }
            }
        }
    }

    private fun checkLogin() {
        val accessToken = sharedPreferences.getString("access_token", null) ?: ""
        Log.e("check", "token= $accessToken")
        if (!accessToken.isNullOrBlank()) {
            viewModel.login(accessToken)
        } else {
            Log.d("checkLogin", "Токен отсутствует. Требуется авторизация.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}