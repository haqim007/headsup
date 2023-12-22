package dev.haqim.headsup.data.mapper

import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.local.entity.SavedArticleEntity
import dev.haqim.headsup.domain.model.Article

fun ArticleEntity.toModel() = Article(
    id = id,
    publishedAt = publishedAt,
    author = author,
    content = content,
    description = description,
    sourceId = sourceId,
    sourceName = sourceName,
    title = title,
    url = url,
    urlToImage = urlToImage,
    isSaved = isSaved == 1
)

fun SavedArticleEntity.toModel() = Article(
    id = id,
    publishedAt = publishedAt,
    author = author,
    content = content,
    description = description,
    sourceId = sourceId,
    sourceName = sourceName,
    title = title,
    url = url,
    urlToImage = urlToImage,
    isSaved = true
)