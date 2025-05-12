package com.example.tracker.viewingAnalyticsCategories.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tracker.R
import com.example.tracker.databinding.AnalyticsFragmentBinding
import com.example.tracker.util.CategoryState
import com.example.tracker.viewingAnalyticsCategories.domain.models.Categories
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class AnalyticsFragment : Fragment(), ViewPagerAdapter.OnDataChangeListener {
    private var _binding: AnalyticsFragmentBinding? = null
    private lateinit var tabMediator: TabLayoutMediator
    private val binding get() = _binding!!
    private val viewModel by viewModel<AnalyticsViewModel>()
    private var adapter = AnalyticsAdapter()
    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.addExpenseBottomSheet)
    }
    private val filterList = mutableListOf<Category>()
    var sum = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnalyticsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val indicatorLayout: LinearLayout = binding.indicatorLayout
        setupViewPager()
        setupIndicators(indicatorLayout)
        setupTabLayout()
        setupObserveLogin()
        viewModel.loadData()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        binding.btSetting.setOnClickListener {
            findNavController().navigate(R.id.action_analyticsFragment_to_settingsFragment)
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.isDraggable = false
        binding.addButton.setOnClickListener {
            binding.addExpenseBottomSheet.isVisible = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.closeAdd.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle, this)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "День"
                1 -> "Неделя"
                2 -> "Месяц"
                3 -> "Год"
                4 -> "Период"
                else -> null
            }
        }
        tabMediator.attach()
    }

    private fun setupIndicators(indicatorLayout: LinearLayout) {
        val numberOfIndicators = 5
        val indicators = mutableListOf<View>()

        for (i in 0 until numberOfIndicators) {
            val indicator = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    marginEnd = 8
                }
                setBackgroundResource(R.drawable.analytics_shape_indicator)
            }
            indicators.add(indicator)
            indicatorLayout.addView(indicator)
        }
        indicators[0].setBackgroundResource(R.drawable.analytics_tab_indicator_enable)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.tabTitle.text = "${getSelectedDay()}"
                    }

                    1 -> {
                        binding.tabTitle.text = "${getSelectedWeek()}"
                    }

                    2 -> {
                        binding.tabTitle.text = "${getSelectedMonth()}"
                    }

                    3 -> {
                        binding.tabTitle.text = "${getSelectedYear()}"
                    }

                    4 -> {
                        binding.tabTitle.text = "${getSelectedPeriod()}"
                    }
                }
                indicators.forEach { it.setBackgroundResource(R.drawable.analytics_shape_indicator) }
                indicators[tab.position].setBackgroundResource(R.drawable.analytics_tab_indicator_enable)
                tab.view.setBackgroundResource(R.drawable.analytics_tab_selector)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundResource(R.drawable.analytics_tab_selector)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun setupTabLayout() {
        binding.tabLayout.getTabAt(0)?.view?.setBackgroundResource(R.drawable.analytics_tab_selector)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDataChange(data: List<Categories>) {
        if (data.isEmpty()) {
            val pieChart: PieChart = binding.pieChart
            pieChart.holeRadius = 85f
            pieChart.description.isEnabled = false
            val pieList = mutableListOf<PieEntry>()
            pieList.add(PieEntry(1f, "No Data"))
            val colors = listOf(ContextCompat.getColor(requireContext(), R.color.ic_gray_primary))
            val pieDataSet = PieDataSet(pieList, "")
            pieDataSet.colors = colors
            val data = PieData(pieDataSet)
            pieChart.setHoleColor(Color.TRANSPARENT)
            pieChart.data = data
            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(R.attr.colorOnPrimaryContainer, typedValue, true)
            val centerTextColor = typedValue.data
            pieChart.setCenterTextColor(centerTextColor)
            pieChart.rotationAngle = 175f
            pieChart.isRotationEnabled = true
            pieChart.setDrawCenterText(true)
            pieDataSet.setDrawValues(false)
            pieChart.legend.isEnabled = false
            val decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault()).apply {
                groupingSeparator = ' '
            }
            val decimalFormat = DecimalFormat("#,###", decimalFormatSymbols)
            val formattedSum = decimalFormat.format(sum)
            pieChart.centerText = "$formattedSum ₽"
            pieChart.setCenterTextSize(20f)
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.bold)
            pieChart.setCenterTextTypeface(typeface)
            pieChart.setDrawEntryLabels(false)
            pieChart.invalidate()
        }
    }

    private fun setupObserveLogin() {
        viewModel.getCategoryState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CategoryState.Empty -> {
                    showEmpty()
                    onDataChange(emptyList())
                }

                is CategoryState.Error -> {
                }

                is CategoryState.Content -> {
                    state.data?.let {
                        Log.d("pspsp", "${state.data}")
                        filterList.addAll(state.data)
                        if (filterList.filter { i -> i.operationSum > 0 }.isEmpty()) {
                            onDataChange(emptyList())
                            showEmpty()
                        } else {
                            addCategory(state.data)
                            showData(filterList.filter { i -> i.operationSum > 0 })
                        }
                    }
                }
            }
        }
    }

    private fun showData(data: List<Category>?) {
        binding.rvAnalytics.isVisible = true
        binding.emptyList.isVisible = false
        binding.rvAnalytics.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAnalytics.adapter = adapter
        adapter.updateItems(data!!)

    }

    private fun showEmpty() {
        binding.rvAnalytics.isVisible = false
        binding.emptyList.isVisible = true
    }

    private fun addCategory(data: List<Category>) {
        val categoriesList = mutableListOf<Categories>()
        for (i in data) {
            val category = Categories(
                name = i.title,
                color = ContextCompat.getColor(requireContext(), i.iconTint),
                sum = i.operationCount
            )
            categoriesList.add(category)
        }
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

    private fun getSelectedDay(): String {
        return "20.04" // Заменить
    }

    private fun getSelectedWeek(): String {
        return "20.04 -27.04" // Заменить
    }

    private fun getSelectedMonth(): String {
        return "Апрель" // Заменить
    }

    private fun getSelectedYear(): String {
        return "2025" // Заменить
    }

    private fun getSelectedPeriod(): String {
        return "1.04-5.04" // Заменить
    }
}