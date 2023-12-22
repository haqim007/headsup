package dev.haqim.headsup.data.mapper

import dev.haqim.headsup.data.local.entity.SavedArticleEntity
import dev.haqim.headsup.domain.model.Article

fun Article.toSavedArticleEntity() = SavedArticleEntity(
    publishedAt = publishedAt,
    author = author,
    urlToImage = urlToImage, 
    description = description, 
    sourceName = sourceName, 
    sourceId = sourceId, 
    title = title, 
    url = url, 
    content = content
)