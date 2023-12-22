package dev.haqim.headsup.data.mapper

import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.remote.response.NewsListResponse

fun NewsListResponse.toEntity() = this.articles.map {
    ArticleEntity(
        publishedAt = it.publishedAt,
        author = it.author,
        content = it.content,
        description = it.description,
        sourceId = it.source.id,
        sourceName = it.source.name,
        title = it.title,
        url = it.url,
        urlToImage = it.urlToImage
    )
}

val hebrewAlphabet = Regex("[\\u0590-\\u05FF]")
fun List<NewsListResponse.Article>.toEntity() = this.map {
    ArticleEntity(
        publishedAt = it.publishedAt,
        author = it.author,
        content = it.content,
        description = it.description,
        sourceId = it.source.id,
        sourceName = it.source.name,
        title = it.title,
        url = it.url,
        urlToImage = it.urlToImage
    )
}.filterNot { it.title.contains(hebrewAlphabet) }