package dev.haqim.headsup.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.local.entity.ArticleRemoteKeys
import dev.haqim.headsup.data.local.entity.SavedArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDatasource @Inject constructor(
    private val database: AppDatabase
) {
    // article remote keys
    suspend fun insertArticleRemoteKey(key: ArticleRemoteKeys){
        database.remoteKeysDao().insert(key)
    }
    suspend fun getArticleRemoteKey(title: String) = 
        database.remoteKeysDao().getRemoteKeyByTitle(title)
    suspend fun clearAllArticleRemoteKey() = 
        database.remoteKeysDao().clearRemoteKeys()
    
    // articles
    suspend fun insertArticles(articles: List<ArticleEntity>){
        database.articleDao().insert(articles)
    }
    fun getArticles(limit: Int, offset: Int): PagingSource<Int, ArticleEntity>{
        return database.articleDao().getArticles(limit, offset)
    }
    fun getArticles(titles: List<String> = listOf()): PagingSource<Int, ArticleEntity>{
        return if(titles.isEmpty()){
            database.articleDao().getArticles()
        }else{
            database.articleDao().getArticles(titles)
        }
    }
    fun getArticles(query: String, titles: List<String> = listOf()): PagingSource<Int, ArticleEntity>{
        return if(titles.isEmpty()){
            database.articleDao().getArticles(query)
        }else{
            database.articleDao().getArticles(query, titles)
        }
    }
    fun getArticles(query: String, limit: Int, offset: Int): PagingSource<Int, ArticleEntity>{
        return database.articleDao().getArticles("%$query%", limit, offset)
    }
    suspend fun clearAllArticles(){
        database.articleDao().clearAll()
    }
    fun getArticle(id: Int): Flow<ArticleEntity?>{
        return database.articleDao().getArticle(id)
    }
    
    suspend fun insertArticlesAndRemoteKey(
        articles: List<ArticleEntity>, 
        remoteKey: ArticleRemoteKeys,
        isRefreshing: Boolean
    ){
        database.withTransaction { 
            if(isRefreshing){
                database.remoteKeysDao().clearRemoteKeys()
                database.articleDao().clearAll()
            }
            database.remoteKeysDao().insert(remoteKey)
            database.articleDao().insert(articles)
        }    
    }
    
    // saved articles
    suspend fun saveArticle(article: SavedArticleEntity){
        database.savedArticleDao().insert(article)
    }
    suspend fun getSavedArticles(limit: Int, offset: Int): List<SavedArticleEntity>{
        return database.savedArticleDao().getArticles(limit, offset)
    }
    suspend fun getSavedArticles(query: String, limit: Int, offset: Int): List<SavedArticleEntity>{
        return database.savedArticleDao().getArticles("%$query%", limit, offset)
    }
    suspend fun unsavedArticle(article: SavedArticleEntity){
        database.savedArticleDao().delete(article.title, article.sourceName)
    }

    fun getSavedArticle(title: String, sourceName: String): Flow<SavedArticleEntity>{
        return database.savedArticleDao().getArticle(title, sourceName)
    }

    fun getSavedArticle(id: Int): Flow<SavedArticleEntity?>{
        return database.savedArticleDao().getArticle(id)
    }
    
    suspend fun unsaveArticle(title: String, sourceName: String){
        database.savedArticleDao().delete(title, sourceName)
    }
    
}