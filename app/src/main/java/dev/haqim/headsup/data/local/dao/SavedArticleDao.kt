package dev.haqim.headsup.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.local.entity.SavedArticleEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface SavedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: SavedArticleEntity)
    
    @Query("SELECT * FROM " +
    "saved_article " +
    "LIMIT :limit OFFSET :offset")
    suspend fun getArticles(limit: Int, offset: Int): List<SavedArticleEntity>

    @Query(
        "SELECT * " +
        "FROM saved_article " +
        "WHERE title Like :query OR description Like :query OR " +
        "content LIKE :query LIMIT :limit OFFSET :offset"
    )
    suspend fun getArticles(query: String, limit: Int, offset: Int): List<SavedArticleEntity>

    @Query(
        "SELECT * " +
        "FROM saved_article " +
        "WHERE title Like :title AND sourceName = :sourceName"
    )
    fun getArticle(title: String, sourceName: String): Flow<SavedArticleEntity>

    @Query(
        "SELECT * " +
        "FROM saved_article " +
        "WHERE id = :id"
    )
    fun getArticle(id: Int): Flow<SavedArticleEntity>
    
    @Query("DELETE FROM saved_article WHERE title = :title AND sourceName = :sourceName")
    suspend fun delete(title: String, sourceName: String)
}