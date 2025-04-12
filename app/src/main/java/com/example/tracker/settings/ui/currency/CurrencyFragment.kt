package com.example.tracker.settings.ui.currency

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tracker.R
import com.example.tracker.databinding.FragmentCurrencyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyBinding
    private val currencyViewModel by viewModel<CurrencyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

        binding.currencyGroup.setOnCheckedChangeListener { group, checkedId ->
            when (binding.currencyGroup.checkedRadioButtonId) {
                R.id.rubOpt -> Log.d("CHECKED", "RUB")
                R.id.usdOpt -> Log.d("CHECKED", "USD")
                R.id.eurOpt -> Log.d("CHECKED", "EUR")
            }
        }

    }
}