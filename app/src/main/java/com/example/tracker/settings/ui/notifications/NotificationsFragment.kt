package com.example.tracker.settings.ui.notifications

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.tracker.R
import com.example.tracker.databinding.FragmentNotificationsBinding
import com.example.tracker.settings.domain.model.Notifications
import com.example.tracker.settings.ui.notifications.model.NotificationFragmentState
import com.example.tracker.settings.utils.TimeFormatterUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val viewModel: NotificationsViewModel by viewModel<NotificationsViewModel>()

    private lateinit var requestNotificationPermissionLauncher : ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestNotificationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (!isGranted) {
                    showPermissionDeniedDialog()
                    binding.notificationSwitch.isChecked = false
                }
            }

        setupViews()
        setupListeners()
        setupObservers()
    }

    private fun setupViews() {
        setupHourPicker()
        setupMinutePicker()
    }
    private fun setupHourPicker() {
        val hourPicker: NumberPicker = binding.hourPicker
        hourPicker.setFormatter { i -> TimeFormatterUtil.formatTwoDigits(i) }
        hourPicker.minValue = 0
        hourPicker.maxValue = 23
    }
    private fun setupMinutePicker() {
        val minutePicker: NumberPicker = binding.minutePicker
        minutePicker.setFormatter { i -> TimeFormatterUtil.formatTwoDigits(i) }
        minutePicker.minValue = 0
        minutePicker.maxValue = 59
    }

    private fun setupListeners() {
        binding.timeCard.setOnClickListener {
            viewModel.onTimeCardClick()
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                requestNotificationPermissionIfNeeded(checked)
            } else {
                viewModel.toggleNotifications(checked)
            }
        }

        setupHourPickerListeners()
        setupMinutePickerListeners()

        binding.notificationMessageEditText.addTextChangedListener {
            viewModel.setMessage(it.toString())
        }
    }
    private fun setupHourPickerListeners() {
        binding.hourPicker.setOnValueChangedListener { _, _, newVal ->
            binding.hours.text = TimeFormatterUtil.formatTwoDigits(newVal)
        }
        binding.hourPicker.setOnScrollListener { _, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                val hours = binding.hourPicker.value
                viewModel.setHours(TimeFormatterUtil.formatTwoDigits(hours))
            }
        }
    }
    private fun setupMinutePickerListeners() {
        binding.minutePicker.setOnValueChangedListener { _, _, newVal ->
            binding.minutes.text = TimeFormatterUtil.formatTwoDigits(newVal)
        }
        binding.minutePicker.setOnScrollListener { _, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                val minutes = binding.minutePicker.value
                viewModel.setMinutes(TimeFormatterUtil.formatTwoDigits(minutes))
            }
        }
    }

    private fun setupObservers() {
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: NotificationFragmentState) {
        when (state) {
            is NotificationFragmentState.Binding -> bindingContent(state.notificationSettings)
            is NotificationFragmentState.ShowNumberPicker -> showNumberPicker()
            is NotificationFragmentState.HideNumberPicker -> hideNumberPicker()
        }
    }
    private fun bindingContent(settings: Notifications) {
        binding.notificationSwitch.isChecked = settings.active
        val (hours, minutes) = settings.time.split(":").map { it }
        binding.hours.text = hours
        binding.hourPicker.value = hours.toInt()
        binding.minutes.text = minutes
        binding.minutePicker.value = minutes.toInt()
        binding.notificationMessageEditText.setText(settings.message)
    }
    private fun showNumberPicker() {
        binding.timeCard.setBackgroundResource(R.drawable.bg_rounded_top_primary)
        binding.numberPickerCard.visibility = View.VISIBLE
    }
    private fun hideNumberPicker() {
        binding.timeCard.setBackgroundResource(R.drawable.bg_rounded_primary)
        binding.numberPickerCard.visibility = View.GONE
    }

    private fun requestNotificationPermissionIfNeeded(isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermissionLauncher.launch(permission)
            } else {
                viewModel.toggleNotifications(isChecked)
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.alertStyle)
            .setTitle(requireContext().getString(R.string.permission_denied))
            .setMessage(requireContext().getString(R.string.permission_denied_message))
            .setPositiveButton(requireContext().getString(R.string.open_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton(requireContext().getString(R.string.cancel), null)
            .show()
    }
}