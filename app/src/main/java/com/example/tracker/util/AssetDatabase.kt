package com.example.tracker.util

import com.example.tracker.R
import com.example.tracker.viewingAnalyticsCategories.domain.models.Category

object AssetDatabase {
    val prepopulateCategories = listOf(
        Category(
            title = "Транспорт",
            icon = R.drawable.category_icon_bus,
            iconColor = R.color.ic_dark_blue_bg,
            iconTint = R.color.ic_dark_blue_primary
        ),
        Category(
            title = "Кафе",
            icon = R.drawable.category_icon_coffee,
            iconColor = R.color.ic_red_bg,
            iconTint = R.color.ic_red_primary
        ),
        Category(
            title = "Медицина",
            icon = R.drawable.category_icon_doctor,
            iconColor = R.color.ic_violet_bg,
            iconTint = R.color.ic_violet_primary
        ),
        Category(
            title = "Дом",
            icon = R.drawable.category_icon_home,
            iconColor = R.color.ic_purple_bg,
            iconTint = R.color.ic_purple_primary
        ),
        Category(
            title = "Авто",
            icon = R.drawable.category_icon_car,
            iconColor = R.color.ic_green_bg,
            iconTint = R.color.ic_green_primary
        ),
        Category(
            title = "Покупки",
            icon = R.drawable.category_icon_shop,
            iconColor = R.color.ic_orange_bg,
            iconTint = R.color.ic_orange_primary
        ),
        Category(
            title = "Еда вне дома",
            icon = R.drawable.category_icon_fastfood,
            iconColor = R.color.ic_light_blue_bg,
            iconTint = R.color.ic_light_blue_primary
        ),
        Category(
            title = "Развлечения",
            icon = R.drawable.category_icon_party,
            iconColor = R.color.ic_light_green_bg,
            iconTint = R.color.ic_light_green_primary
        ),
        Category(
            title = "Игры",
            icon = R.drawable.category_icon_gamepad,
            iconColor = R.color.ic_blue_bg,
            iconTint = R.color.ic_blue_primary
        ),
    )
}