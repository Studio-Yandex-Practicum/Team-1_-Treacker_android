package com.example.tracker.viewingAnalyticsCategories.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracker.viewingAnalyticsCategories.domain.models.Categories

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val listener: OnDataChangeListener
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnalyticsDayFragment()
            1 -> AnalyticsWeekFragment()
            2 -> AnalyticsMonthFragment()
            3 -> AnalyticsYearFragment()
            4 -> AnalyticsPeriodFragment()
            else -> throw IllegalArgumentException("Неверная позиция: $position")
        }
    }

    interface OnDataChangeListener {
        fun onDataChange(data: List<Categories>)
    }
}