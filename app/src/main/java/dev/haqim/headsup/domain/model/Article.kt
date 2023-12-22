package dev.haqim.headsup.domain.model

data class Article(
    val id: Int,
    val publishedAt: String,
    val author: String?,
    val urlToImage: String?,
    val description: String?,
    val sourceName: String,
    val sourceId: String?,
    val title: String,
    val url: String,
    val content: String?,
    val isSaved: Boolean
)

val dummyArticles = arrayOf(
    Article(
        id = 1,
        sourceId = "google-news",
        sourceName = "Google News",
        author = "SiliconRepublic.com",
        title = "Irish start-up Kerno raises €1.69m for its troubleshooting tech - SiliconRepublic.com",
        description = null,
        url = "https://news.google.com/rss/articles/CBMiUmh0dHBzOi8vd3d3LnNpbGljb25yZXB1YmxpYy5jb20vc3RhcnQtdXBzL2tlcm5vLXNlZWQtZnVuZGluZy10cm91Ymxlc2hvb3RpbmctY2xvdWTSAQA?oc=5",
        urlToImage = null,
        publishedAt = "2023-12-21T08:34:48Z",
        content = null,
        isSaved = false
    ),
    Article(
        id = 1,
        sourceId = "google-news",
        sourceName = "Google News",
        author = "SiliconRepublic.com",
        title = "Irish start-up Kerno raises €1.69m for its troubleshooting tech - SiliconRepublic.com",
        description = null,
        url = "https://news.google.com/rss/articles/CBMiUmh0dHBzOi8vd3d3LnNpbGljb25yZXB1YmxpYy5jb20vc3RhcnQtdXBzL2tlcm5vLXNlZWQtZnVuZGluZy10cm91Ymxlc2hvb3RpbmctY2xvdWTSAQA?oc=5",
        urlToImage = null,
        publishedAt = "2023-12-21T08:34:48Z",
        content = null,
        isSaved = false
    ),
    Article(
        id = 1,
        sourceId = "google-news",
        sourceName = "Google News",
        author = "SiliconRepublic.com",
        title = "Irish start-up Kerno raises €1.69m for its troubleshooting tech - SiliconRepublic.com",
        description = null,
        url = "https://news.google.com/rss/articles/CBMiUmh0dHBzOi8vd3d3LnNpbGljb25yZXB1YmxpYy5jb20vc3RhcnQtdXBzL2tlcm5vLXNlZWQtZnVuZGluZy10cm91Ymxlc2hvb3RpbmctY2xvdWTSAQA?oc=5",
        urlToImage = null,
        publishedAt = "2023-12-21T08:34:48Z",
        content = null,
        isSaved = false
    ),
    Article(
        id = 1,
        sourceId = "google-news",
        sourceName = "Google News",
        author = "SiliconRepublic.com",
        title = "Irish start-up Kerno raises €1.69m for its troubleshooting tech - SiliconRepublic.com",
        description = null,
        url = "https://news.google.com/rss/articles/CBMiUmh0dHBzOi8vd3d3LnNpbGljb25yZXB1YmxpYy5jb20vc3RhcnQtdXBzL2tlcm5vLXNlZWQtZnVuZGluZy10cm91Ymxlc2hvb3RpbmctY2xvdWTSAQA?oc=5",
        urlToImage = null,
        publishedAt = "2023-12-21T08:34:48Z",
        content = null,
        isSaved = false
    )
)
