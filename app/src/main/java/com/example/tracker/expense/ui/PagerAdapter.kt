package com.example.tracker.expense.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.expense.domain.models.Category

class PagerAdapter(categories: List<Category>) : RecyclerView.Adapter<PagerViewHolder>() {
    private val gridCategories = categories.chunked(9)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_grid_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gridCategories.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(gridCategories[position])
    }
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val gridView = itemView.findViewById<GridView>(R.id.grid_view)
    fun bind(categories: List<Category>) {
        val adapter = CardAdapter(categories, itemView.context)
        gridView.adapter = adapter
    }
}