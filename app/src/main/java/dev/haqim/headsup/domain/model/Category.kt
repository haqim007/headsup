package dev.haqim.headsup.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import dev.haqim.headsup.R

data class Category(
    val id: String,
    val name: String,
    @DrawableRes
    val icon: Int,
    val isSelected: Boolean = false
)

val categories = arrayOf(
    Category(
        id = "general",
        name = "General",
        icon = R.drawable.ic_spoke_24
    ),
    Category(
        id = "technology",
        name = "Tech",
        icon = R.drawable.ic_computer_24
    ),
    Category(
        id = "business",
        name = "Business",
        icon = R.drawable.ic_briefcase_24
    ),
    Category(
        id = "entertainment",
        name = "Entertainment",
        icon = R.drawable.ic_movie_filter_24
    ),
    Category(
        id = "health",
        name = "Health",
        icon = R.drawable.ic_health_and_safety_24
    ),
    Category(
        id = "science",
        name = "Science",
        icon = R.drawable.ic_science_24
    ),
    Category(
        id = "sports",
        name = "Sports",
        icon = R.drawable.ic_sports_score_24
    )
)
