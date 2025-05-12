package com.example.tracker.viewingAnalyticsCategories.ui

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tracker.R
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category
import com.google.android.material.shape.MaterialShapeDrawable

class AnalyticsViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
    private val categoryName: TextView = itemView.findViewById(R.id.category_name)
    private val sum: TextView = itemView.findViewById(R.id.sum)
    private val count: TextView = itemView.findViewById(R.id.count)
    private val percent: TextView = itemView.findViewById(R.id.percent)
    private val bgImage: ImageView = itemView.findViewById(R.id.bg_image)

    fun bind(item: Category, onItemClickListener: OnItemClickListener?) {
        categoryName.text = item.title
        sum.text = "${item.operationSum} ₽"
        count.text = item.operationCount.toString()
        percent.text = "${item.percentageShare} %"
        val context = itemView.context
        val cornerSizeInDp = 16
        val scale = context.resources.displayMetrics.density
        val cornerSizeInPx = (cornerSizeInDp * scale + 0.5f).toInt()
        val shapeDrawable = MaterialShapeDrawable().apply {
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setAllCornerSizes(cornerSizeInPx.toFloat())
                .build()
            fillColor = ColorStateList.valueOf(ContextCompat.getColor(context, item.iconTint))
        }
        bgImage.background = shapeDrawable


        Glide.with(itemView)
            .load(item.icon)
            .centerInside()
            .into(categoryImage)
        categoryImage.setColorFilter(
            ContextCompat.getColor(context, item.iconColor),
            android.graphics.PorterDuff.Mode.SRC_IN
        )


        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: Category)
    }
}