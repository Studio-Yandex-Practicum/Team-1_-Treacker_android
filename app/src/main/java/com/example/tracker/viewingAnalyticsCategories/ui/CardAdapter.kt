package com.example.tracker.viewingAnalyticsCategories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.databinding.ItemAddCategoryBinding
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category

class CardAdapter(
    private val categories: List<Category>
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: ItemAddCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemAddCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val category = categories[position]
        val itemResource = holder.binding.root.resources
        holder.binding.apply {
            title.text = category.title
            icon.setImageResource(category.icon)
            icon.setColorFilter(itemResource.getColor(category.iconTint))
            iconCard.setCardBackgroundColor(itemResource.getColor(category.iconColor))
        }
    }
}
