package dev.haqim.headsup.data.remotemediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.haqim.headsup.data.local.LocalDatasource
import dev.haqim.headsup.data.local.entity.SavedArticleEntity
import dev.haqim.headsup.data.mapper.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SavedArticlesPagingSource (
    private val localDataSource: LocalDatasource,
    private val query: String? = null
): PagingSource<Int, SavedArticleEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SavedArticleEntity> {

        val position = params.key ?: 0
        val offset = position * 30

        return try {
            val savedArticleEntities = withContext(Dispatchers.IO){
                if (query == null){
                    localDataSource.getSavedArticles(limit = 30, offset = offset)
                }else{
                    localDataSource.getSavedArticles(limit = 30, offset = offset, query = query)
                }
            }

            val nextKey = if (savedArticleEntities.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / 30)
            }
            LoadResult.Page(
                data = savedArticleEntities,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, SavedArticleEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
