package dev.haqim.headsup.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("saved_article",
    indices = [
        Index(value = ["title", "sourceName", "url"], unique = true)
    ]
)
data class SavedArticleEntity(
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
    val content: String?
)
