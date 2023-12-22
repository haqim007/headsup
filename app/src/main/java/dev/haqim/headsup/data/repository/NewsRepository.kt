package dev.haqim.headsup.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.haqim.headsup.data.local.LocalDatasource
import dev.haqim.headsup.data.mapper.toModel
import dev.haqim.headsup.data.mapper.toSavedArticleEntity
import dev.haqim.headsup.data.remote.RemoteDatasource
import dev.haqim.headsup.data.remotemediator.ArticlesRemoteMediator
import dev.haqim.headsup.data.remotemediator.ArticlesRemoteMediator.Companion.PAGE_SIZE
import dev.haqim.headsup.data.remotemediator.SavedArticlesPagingSource
import dev.haqim.headsup.di.DispatcherIO
import dev.haqim.headsup.domain.model.Article
import dev.haqim.headsup.domain.model.Category
import dev.haqim.headsup.domain.repository.INewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDatasource,
    private val localDatasource: LocalDatasource,
    @DispatcherIO
    private val dispatcher: CoroutineDispatcher
): INewsRepository {

    override fun getArticle(id: Int): Flow<Article?> {
        return localDatasource.getArticle(id).map { it?.toModel() }
    }

    override fun getSavedArticle(id: Int): Flow<Article?> {
        return localDatasource.getSavedArticle(id).map { it?.toModel() }
    }

    override fun getArticles(query: String?, category: Category): Flow<PagingData<Article>> {
        var titles = listOf<String>()
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = ArticlesRemoteMediator(
                fetchArticles = { page: Int, pageSize: Int ->  
                    val res = remoteDataSource.getTopHeadlines(
                        page = page,
                        pageSize = pageSize,
                        category = category.id
                    )
                    if (res.isSuccess){
                        titles = res.getOrNull()?.articles?.map { it.title } ?: listOf()
                    }
                    res
                },
                insertArticlesAndRemoteKey = {articles, remoteKey, isRefresh -> 
                    withContext(dispatcher){
                        localDatasource.insertArticlesAndRemoteKey(
                            articles, remoteKey,  isRefresh
                        )
                    }
                },
                getArticleRemoteKey = {title ->  
                    localDatasource.getArticleRemoteKey(title)
                }
            ),
            pagingSourceFactory = {
                if (query != null){
                    localDatasource.getArticles(query, titles)
                }else{
                    localDatasource.getArticles(titles)
                }
            }
        ).flow.flowOn(dispatcher).map { pagingData ->
            pagingData.map {
                it.toModel()
            }
        }
    }

    override fun getArticle(article: Article): Flow<Article?> {
        return localDatasource.getArticle(article.id).map { it?.toModel() }
    }

    override fun getSavedArticles(query: String?): Flow<PagingData<Article>> {
        return  Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                SavedArticlesPagingSource(localDatasource, query)
            }
        ).flow.flowOn(dispatcher).map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    override suspend fun saveArticle(article: Article) {
        if (article.isSaved){
            localDatasource.unsaveArticle(article.title, article.sourceName)
        }else{
            localDatasource.saveArticle(article.toSavedArticleEntity())
        }
    }
}