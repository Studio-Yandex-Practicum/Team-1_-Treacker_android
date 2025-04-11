package com.example.tracker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tracker.App
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

        binding.themeSwitch.isChecked = settingsViewModel.getThemeSettings()

        settingsViewModel.getThemeSettingsLiveData().observe(viewLifecycleOwner) {
            (requireContext().applicationContext as App).switchTheme(it)
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            settingsViewModel.switchTheme(checked)
        }

    }
}