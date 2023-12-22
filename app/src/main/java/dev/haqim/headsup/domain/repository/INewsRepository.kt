package dev.haqim.headsup.domain.repository

import androidx.paging.PagingData
import dev.haqim.headsup.domain.model.Article
import dev.haqim.headsup.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    // articles
    fun getArticles(query: String? = null, category: Category): Flow<PagingData<Article>>
    fun getArticle(article: Article): Flow<Article?>
    fun getArticle(id: Int): Flow<Article?>
    
    
    //saved articles
    fun getSavedArticles(query: String? = null): Flow<PagingData<Article>>
    suspend fun saveArticle(article: Article)
    fun getSavedArticle(id: Int): Flow<Article?>
}