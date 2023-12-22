package dev.haqim.headsup.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.headsup.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<ArticleEntity>)
    
    @Query("SELECT " +
    "article.id as id, "+
    "article.title as title ," +
    "article.publishedAt as publishedAt ," +
    "article.urlToImage as urlToImage ," +
    "article.description as description ," +
    "article.sourceName as sourceName ," +
    "article.sourceId as sourceId ," +
    "article.url as url ," +
    "article.content as content ," +
    "article.author as author ," +
    "CASE " +
    "WHEN saved_article.url IS NULL THEN 0 " +
    "ELSE 1 " +
    "END AS isSaved " +
    "FROM article " +
    "LEFT JOIN saved_article ON saved_article.title == article.title " +
    "AND saved_article.sourceName == article.sourceName " +
    "LIMIT :limit OFFSET :offset")
    fun getArticles(limit: Int, offset: Int): PagingSource<Int, ArticleEntity>

    @Query("SELECT " +
    "article.id as id, "+
    "article.title as title ," +
    "article.publishedAt as publishedAt ," +
    "article.urlToImage as urlToImage ," +
    "article.description as description ," +
    "article.sourceName as sourceName ," +
    "article.sourceId as sourceId ," +
    "article.url as url ," +
    "article.content as content ," +
    "article.author as author ," +
    "CASE " +
    "WHEN saved_article.url IS NULL THEN 0 " +
    "ELSE 1 " +
    "END AS isSaved " +
    "FROM article " +
    "LEFT JOIN saved_article ON saved_article.title == article.title " +
    "AND saved_article.sourceName == article.sourceName")
    fun getArticles(): PagingSource<Int, ArticleEntity>

    @Query("SELECT " +
    "article.id as id, "+
    "article.title as title ," +
    "article.publishedAt as publishedAt ," +
    "article.urlToImage as urlToImage ," +
    "article.description as description ," +
    "article.sourceName as sourceName ," +
    "article.sourceId as sourceId ," +
    "article.url as url ," +
    "article.content as content ," +
    "article.author as author ," +
    "CASE " +
    "WHEN saved_article.url IS NULL THEN 0 " +
    "ELSE 1 " +
    "END AS isSaved " +
    "FROM article " +
    "LEFT JOIN saved_article ON saved_article.title == article.title " +
    "AND saved_article.sourceName == article.sourceName " +
    "WHERE article.title in (:titles)" +
    "")
    fun getArticles(titles: List<String>): PagingSource<Int, ArticleEntity>

    @Query(
        "SELECT " +
        "article.id as id, "+
        "article.title as title ," +
        "article.publishedAt as publishedAt ," +
        "article.urlToImage as urlToImage ," +
        "article.description as description ," +
        "article.sourceName as sourceName ," +
        "article.sourceId as sourceId ," +
        "article.url as url ," +
        "article.content as content ," +
        "article.author as author ," +
        " CASE " +
        "WHEN saved_article.url IS NULL THEN 0 " +
        "ELSE 1 " +
        "END AS isSaved " +
        "FROM article " +
        "LEFT JOIN saved_article ON saved_article.title == article.title " +
        "AND saved_article.sourceName == article.sourceName " +
        "WHERE article.title Like :query OR article.description Like :query OR " +
        "article.content LIKE :query OR article.sourceName LIKE :query"
    )
    fun getArticles(query: String): PagingSource<Int, ArticleEntity>

    @Query(
        "SELECT " +
        "article.id as id, "+
        "article.title as title ," +
        "article.publishedAt as publishedAt ," +
        "article.urlToImage as urlToImage ," +
        "article.description as description ," +
        "article.sourceName as sourceName ," +
        "article.sourceId as sourceId ," +
        "article.url as url ," +
        "article.content as content ," +
        "article.author as author ," +
        " CASE " +
        "WHEN saved_article.url IS NULL THEN 0 " +
        "ELSE 1 " +
        "END AS isSaved " +
        "FROM article " +
        "LEFT JOIN saved_article ON saved_article.title == article.title " +
        "AND saved_article.sourceName == article.sourceName " +
        "WHERE article.title in (:titles) AND (article.title Like :query OR article.description Like :query OR " +
        "article.content LIKE :query OR article.sourceName LIKE :query)"
    )
    fun getArticles(query: String, titles: List<String>): PagingSource<Int, ArticleEntity>
    
    @Query(
        "SELECT " +
        "article.id as id, "+
        "article.title as title ," +
        "article.publishedAt as publishedAt ," +
        "article.urlToImage as urlToImage ," +
        "article.description as description ," +
        "article.sourceName as sourceName ," +
        "article.sourceId as sourceId ," +
        "article.url as url ," +
        "article.content as content ," +
        "article.author as author ," +
        " CASE " +
        "WHEN saved_article.url IS NULL THEN 0 " +
        "ELSE 1 " +
        "END AS isSaved " +
        "FROM article " +
        "LEFT JOIN saved_article ON saved_article.title == article.title " +
        "AND saved_article.sourceName == article.sourceName " +
        "WHERE article.title Like :query OR article.description Like :query OR " +
        "article.content LIKE :query OR article.sourceName LIKE :query LIMIT :limit OFFSET :offset"
    )
    fun getArticles(query: String, limit: Int, offset: Int): PagingSource<Int, ArticleEntity>

    @Query(
        "SELECT * " +
        "FROM article " +
        "WHERE id Like :id"
    )
    fun getArticle(id: Int): Flow<ArticleEntity?>
    
    @Query("DELETE FROM article")
    suspend fun clearAll()
}