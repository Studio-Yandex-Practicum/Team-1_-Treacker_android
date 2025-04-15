package com.example.tracker.expense.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.databinding.ItemGridCardBinding
import com.example.tracker.expense.domain.models.Category

class PagerAdapter(categories: List<Category>) : RecyclerView.Adapter<PagerViewHolder>() {
    private val gridCategories = categories.chunked(9)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            ItemGridCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return gridCategories.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(gridCategories[position])
    }
}

class PagerViewHolder(private val binding: ItemGridCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(categories: List<Category>) {
        val adapter = CardAdapter(categories)
        val gridLayoutManager = GridLayoutManager(binding.root.context, 5)
        binding.recycler.layoutManager = gridLayoutManager
        binding.recycler.adapter = adapter
    }
}