package com.example.tracker.expense.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.tracker.R
import com.example.tracker.expense.domain.models.Category

class CardAdapter(
    private val categories: List<Category>,
    private val context: Context
) : BaseAdapter() {
    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_grid_card, parent, false)

        val category = categories[position]

        val title = view.findViewById<TextView>(R.id.title)
        val icon = view.findViewById<ImageView>(R.id.icon)
        val iconCard = view.findViewById<CardView>(R.id.icon_card)
        val resources = view.resources

        title.text = category.title
        icon.setImageResource(category.icon)
        icon.setColorFilter(category.iconColor)
        iconCard.setCardBackgroundColor(resources.getColor(category.iconTint))

        return view
    }
}