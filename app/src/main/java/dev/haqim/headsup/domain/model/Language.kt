package dev.haqim.headsup.domain.model

import androidx.annotation.DrawableRes
import dev.haqim.headsup.R

data class Language(
    val id: String?,
    val name: String,
    @DrawableRes
    val flag: Int
)

//ar, de, en, es, fr, it, nl, no, pt, ru, sv, zh
val languages = arrayOf(
    Language(
        id = null,
        name = "All",
        flag = R.drawable.globe_2
    ),
    Language(
        id = "id",
        name = "ID",
        flag = R.drawable.indonesia_flag
    ),
    Language(
        id = "en",
        name = "EN",
        flag = R.drawable.uk_flag
    ),
    Language(
        id = "de",
        name = "DE",
        flag = R.drawable.german_flag
    ),
    Language(
        id = "it",
        name = "It",
        flag = R.drawable.italy_flag
    ),
    Language(
        id = "fr",
        name = "FR",
        flag = R.drawable.france_flag
    ),
    Language(
        id = "nl",
        name = "NL",
        flag = R.drawable.netherlands_flag
    ),
    Language(
        id = "no",
        name = "NO",
        flag = R.drawable.norway_flag
    )
)
