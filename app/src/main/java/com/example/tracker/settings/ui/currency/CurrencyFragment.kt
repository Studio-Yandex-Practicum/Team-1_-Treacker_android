package com.example.tracker.settings.ui.currency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

//        val rub = binding.currencyGroup.getChildAt(0) as RadioButton
//        val usd = binding.currencyGroup.getChildAt(2) as RadioButton
//        val eur = binding.currencyGroup.getChildAt(4) as RadioButton
//
//        rub.text = "Российский рубль, ₽"
//        usd.text = "Доллар США, $"
//        eur.text = "Евро, €"

    }
}