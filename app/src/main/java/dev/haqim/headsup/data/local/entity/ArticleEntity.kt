package dev.haqim.headsup.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "article",
    indices = [
        Index(value = ["title", "sourceName", "url"], unique = true)
    ]
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val publishedAt: String,
    val author: String?,
    val urlToImage: String?,
    val description: String?,
    val sourceName: String,
    val sourceId: String?,
    val title: String,
    val url: String,
    val content: String?,
    val isSaved: Int = 0
)
