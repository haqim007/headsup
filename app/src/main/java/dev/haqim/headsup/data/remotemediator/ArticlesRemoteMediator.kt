package dev.haqim.headsup.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.local.entity.ArticleRemoteKeys
import dev.haqim.headsup.data.mapper.toEntity
import dev.haqim.headsup.data.remote.response.NewsListResponse
import okio.IOException
import retrofit2.HttpException


@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val fetchArticles: suspend (
        page: Int, 
        pageSize: Int
    )-> Result<NewsListResponse>,
    private val insertArticlesAndRemoteKey: suspend (
        articles: List<ArticleEntity>,
        remoteKey: ArticleRemoteKeys,
        isRefresh: Boolean
    ) -> Unit,
    private val  getArticleRemoteKey: suspend(
        title: String
    ) -> ArticleRemoteKeys?
): RemoteMediator<Int, ArticleEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleEntity>): MediatorResult {
        
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys: ArticleRemoteKeys? = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys: ArticleRemoteKeys? = getRemoteKeyForFirstItem(state)
                val prevKey: Int = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys: ArticleRemoteKeys? = getRemoteKeyForLastItem(state)
                val nextKey: Int = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val result = try {
            val response = fetchArticles(page, state.config.pageSize)
            val endOfPaginationReached = response.getOrNull()?.articles?.isEmpty() ?: true

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val articles = response.getOrNull()?.articles ?: listOf()
            val key = ArticleRemoteKeys(
                articles.last().title,
                prevKey,
                nextKey
            )

            insertArticlesAndRemoteKey(
                articles.toEntity(),
                key,
                loadType == LoadType.REFRESH
            )
            
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }
        catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
        catch (e: Exception){
            MediatorResult.Error(e)
        }

        return result

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                getArticleRemoteKey(title)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys?{
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { data ->
                getArticleRemoteKey(data.title)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys?{
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            getArticleRemoteKey(data.title)
        }
    }

     companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val PAGE_SIZE = 30
    }
}