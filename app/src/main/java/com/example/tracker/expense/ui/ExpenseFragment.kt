package com.example.tracker.expense.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tracker.databinding.FragmentExpenseBinding
import com.example.tracker.expense.domain.models.Category
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExpenseFragment : Fragment() {

    private val viewModel by viewModel<ExpenseViewModel>()

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
                  renderState(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: ExpenseScreenState) {
        when (state) {
            is ExpenseScreenState.AddExpenseState -> addExpenseUiState(state)
            ExpenseScreenState.CreateCategory -> TODO()
        }
    }

    private fun addExpenseUiState(state: ExpenseScreenState.AddExpenseState) {
        val pagerAdapter = PagerAdapter(state.categories)
        binding.viewPager.adapter = pagerAdapter

        val dateText = state.date.toIntOrNull()
        if (dateText == null) {
            binding.dateEditText.setText(state.date)
        } else {
            binding.dateEditText.setText(dateText)
        }
    }
}