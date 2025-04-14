package com.example.tracker.settings.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.FragmentCurrencyBinding
import com.example.tracker.settings.domain.model.Currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyBinding
    private val currencyViewModel by viewModel<CurrencyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val chekedId = currencyViewModel.getCheckedId()

        when (chekedId) {
            R.id.rubOpt -> binding.rubOpt.isChecked = true
            R.id.usdOpt -> binding.usdOpt.isChecked = true
            R.id.eurOpt -> binding.eurOpt.isChecked = true
        }
    }

    private fun setupListeners() {
        binding.currencyGroup.setOnCheckedChangeListener { group, checkedId ->
            when (binding.currencyGroup.checkedRadioButtonId) {
                R.id.rubOpt -> currencyViewModel.setCurrency(Currency.RUB)
                R.id.usdOpt -> currencyViewModel.setCurrency(Currency.USD)
                R.id.eurOpt -> currencyViewModel.setCurrency(Currency.EUR)
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}