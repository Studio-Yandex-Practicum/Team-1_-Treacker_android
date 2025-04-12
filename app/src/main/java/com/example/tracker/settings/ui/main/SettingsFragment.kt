package com.example.tracker.settings.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tracker.App
import com.example.tracker.R
import com.example.tracker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
        setupListeners()
    }

    private fun setupViews() {
        binding.themeSwitch.isChecked = settingsViewModel.getThemeSettings()
    }

    private fun setupObservers() {
        settingsViewModel.getThemeSettingsLiveData().observe(viewLifecycleOwner) {
            (requireContext().applicationContext as App).switchTheme(it)
        }
    }
    private fun setupListeners() {
        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            settingsViewModel.switchTheme(checked)
        }

        binding.currencyCard.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_currencyFragment)
        }
    }
}