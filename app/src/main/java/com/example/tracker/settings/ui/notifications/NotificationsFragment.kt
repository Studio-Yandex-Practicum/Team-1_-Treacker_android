package com.example.tracker.settings.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.tracker.databinding.FragmentNotificationsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    private val viewModel: NotificationsViewModel by viewModel<NotificationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val hourPicker: NumberPicker = binding.hourPicker
        val minutePicker: NumberPicker = binding.minutePicker

        hourPicker.setFormatter { i -> String.format("%02d", i) }
        minutePicker.setFormatter { i -> String.format("%02d", i) }

        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        hourPicker.value = 12
        minutePicker.value = 0
    }
}