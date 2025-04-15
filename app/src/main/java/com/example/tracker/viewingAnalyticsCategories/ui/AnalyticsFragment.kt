package com.example.tracker.viewingAnalyticsCategories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.tracker.R
import com.example.tracker.databinding.AnalyticsFragmentBinding
import com.example.tracker.viewingAnalyticsCategories.domain.Categories
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class AnalyticsFragment : Fragment(), ViewPagerAdapter.OnDataChangeListener {
    private var _binding: AnalyticsFragmentBinding? = null
    private lateinit var tabMediator: TabLayoutMediator
    private val binding get() = _binding!!

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
        onDataChange(emptyList())

        setupViewPager()
        setupIndicators(indicatorLayout)
        setupTabLayout()
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
            val sum = 0
            val pieList = mutableListOf<PieEntry>()
            pieList.add(PieEntry(1f, "No Data"))
            val colors = listOf(ContextCompat.getColor(requireContext(), R.color.ic_gray_primary))
            val pieDataSet = PieDataSet(pieList, "")
            pieDataSet.colors = colors
            val data = PieData(pieDataSet)
            pieChart.data = data
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
}