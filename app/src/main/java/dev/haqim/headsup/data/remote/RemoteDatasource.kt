package dev.haqim.headsup.data.remote

import dev.haqim.headsup.data.remote.response.NewsListResponse
import dev.haqim.headsup.data.remote.service.NewsListService
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val service: NewsListService
) {
    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        category:String,
        query: String? = null,
        language:String? = null
    ): Result<NewsListResponse>{
        return try {
            Result.success(
                service.getTopHeadlines(query, page, pageSize, category,language)
            )
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}